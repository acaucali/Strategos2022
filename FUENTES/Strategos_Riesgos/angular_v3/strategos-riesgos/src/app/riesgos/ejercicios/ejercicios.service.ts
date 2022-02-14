import { Injectable } from '@angular/core';
import { formatDate } from '@angular/common';
import { Observable, of , throwError, from} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { toInteger } from '@ng-bootstrap/ng-bootstrap/util/util';

import { Ejercicios } from './ejercicios';
import { FactorRiesgo } from '../factorriesgo/factor';
import { URL_BACKEND } from 'src/app/config/config';


@Injectable()

export class EjerciciosService {
  private urlEndPoint:string =URL_BACKEND+'/api/ejercicios/ejercicio';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  public factores: FactorRiesgo[];
  public ejercicios: Ejercicios[];

  constructor(private http: HttpClient, private router: Router) { }

  getEjerciciosList(id: number){
    return this.http.get(this.urlEndPoint+'/proceso/'+id).pipe(map(res =>{
      this.ejercicios = res as Ejercicios[];
      return this.ejercicios;
    }));
  }

  getEjercicios(id: number, page: number): Observable<any> {
    //return of(Ejercicios);
    return this.http.get(this.urlEndPoint+ '/page/'+id+'/'+page).pipe(
      map((response: any) => {
        (response.content as Ejercicios[]).map(ejercicio=>{
          return ejercicio;
        });
        return response;
      })
    );
  }

  create(ejercicio: Ejercicios) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, ejercicio, {headers: this.httpHeaders}).pipe(
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

  getEjercicio(id): Observable<Ejercicios>{
    return this.http.get<Ejercicios>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/ejercicios']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(ejercicio: Ejercicios): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${ejercicio.ejercicioId}`, ejercicio, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Ejercicios>{
    return this.http.delete<Ejercicios>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
  
  getFactores(id: number){
    return this.http.get(`${this.urlEndPoint}/factor/${id}`).pipe(map(res => {
      this.factores = res as FactorRiesgo[];
        return this.factores;
    }));
    
  }
}

