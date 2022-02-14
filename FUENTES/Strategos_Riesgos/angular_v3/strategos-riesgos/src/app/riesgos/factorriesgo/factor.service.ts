import { Injectable } from '@angular/core';
import { formatDate } from '@angular/common';
import { Observable, of , throwError, from} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router, Resolve } from '@angular/router';

import { toInteger } from '@ng-bootstrap/ng-bootstrap/util/util';

import { FactorRiesgo } from './factor';
import { Ejercicios } from '../ejercicios/ejercicios';
import { CausaFactor } from './causas/causaFactor';
import { EfectoFactor } from './efectos/efectoFactor';
import { ControlFactor } from './controles/controlFactor';
import { Procesos } from 'src/app/procesos/procesos';
import { URL_BACKEND } from 'src/app/config/config';
import { MapaCalor } from './mapa/mapaCalor';



@Injectable()

export class FactorService implements Resolve<any>{
  resolve(route: import("@angular/router").ActivatedRouteSnapshot, state: import("@angular/router").RouterStateSnapshot) {
    throw new Error("Method not implemented.");
  }
  private urlEndPoint:string =URL_BACKEND+'/api/ejercicios/factor';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  public causas: CausaFactor[];
  public efectos: EfectoFactor[];
  public controles: ControlFactor[];
  public factores: FactorRiesgo[];
  public resultados: number[];
  public objetosMapa: MapaCalor[];


  constructor(private http: HttpClient, private router: Router) { }

  getFactores(id: number, page: number): Observable<any> {
    //return of(Ejercicios);
    return this.http.get(this.urlEndPoint+ '/page/'+id+'/'+page).pipe(
      map((response: any) => {
        (response.content as FactorRiesgo[]).map(factor=>{
          return factor;
        });
        return response;
      })
    );
  }

  getFactoresProceso(procesoId: number){
    return this.http.get(`${this.urlEndPoint}/proceso/${procesoId}`).pipe(map(res => {
      this.factores = res as FactorRiesgo[];
        return this.factores;
    }));
  }

  create(factor: FactorRiesgo, ejercicio: Ejercicios, proceso: Procesos) : Observable<any>{
    return this.http.post<any>(`${this.urlEndPoint}/${ejercicio.ejercicioId}/${proceso.procesosId}`, factor, {headers: this.httpHeaders}).pipe(
      catchError(e =>{
        if(e.status==400){
          return throwError(e);
        }
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje,e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getFactor(id: number): Observable<FactorRiesgo>{
    return this.http.get<FactorRiesgo>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/factores']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(factor: FactorRiesgo): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${factor.factorRiesgoId}`, factor, {headers: this.httpHeaders }).pipe(
      catchError(e =>{
        if(e.status==400){
          return throwError(e);
        }
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  delete(id: number): Observable<FactorRiesgo>{
    return this.http.delete<FactorRiesgo>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getCausas(id: number){
    return this.http.get(`${this.urlEndPoint}/causas/${id}`).pipe(map(res => {
      this.causas = res as CausaFactor[];
        return this.causas;
    }));
    
  }

  getEfectos(id: number){
    return this.http.get(`${this.urlEndPoint}/efectos/${id}`).pipe(map(res => {
      this.efectos = res as EfectoFactor[];
        return this.efectos;
    }));
    
  }

  getControles(id: number){
    return this.http.get(`${this.urlEndPoint}/controles/${id}`).pipe(map(res => {
      this.controles = res as ControlFactor[];
        return this.controles;
    }));
    
  }

  getNumeroFactoresImpacto(id: number){
    return this.http.get(`${this.urlEndPoint}/impacto/${id}`).pipe(map(res => {
      this.resultados = res as number[];
      return this.resultados;
    }));
    
  }

  getNumeroFactoresProbabilidad(id: number){
    return this.http.get(`${this.urlEndPoint}/probabilidad/${id}`).pipe(map(res => {
      this.resultados = res as number[];
      return this.resultados;
    }));
    
  }

  getFactoresSeveridad(severidad: number){
    return this.http.get(`${this.urlEndPoint}/severidad/${severidad}`).pipe(map(res => {
      this.factores = res as FactorRiesgo[];
        return this.factores;
    }));
    
  }

  getNumeroFactoresCausas(id: number){
    return this.http.get(`${this.urlEndPoint}/causas-factor/${id}`).pipe(map(res => {
      this.resultados = res as number[];
      return this.resultados;
    }));
    
  }

  getMapasCalor(id: number){
    return this.http.get(`${this.urlEndPoint}/mapa-calor/${id}`).pipe(map(res => {
      this.objetosMapa = res as MapaCalor[];
      return this.objetosMapa;
    }));
    
  }
  
}
