import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { TiposPropuestas } from '../model/tipospropuestas';
import { Plan } from '../model/plan';
import { ProyectosPlan } from '../model/proyectosplan';
import { Perspectiva } from '../model/perspectiva';
import { Arbol } from '../model-util/arbol';
import { Indicador } from '../model/indicador';



@Injectable({
  providedIn: 'root'
})

export class IndicadorService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/indicador';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public indicadores: Indicador[];
   

  constructor(private http: HttpClient, private router: Router) { }

  getIndicadoresList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.indicadores = res as Indicador[];
      return this.indicadores;
    }));
  }

  getIndicadoresPerspectiva(id: number){
    return this.http.get(`${this.urlEndPoint}/perspectiva/${id}`).pipe(map(res =>{
      this.indicadores = res as Indicador[];
      return this.indicadores;
    }));
  }

  getIndicadorPage(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Indicador[]).map(ind=>{
          return ind;
        });
        return response;
      })
    );
  }

  create(indicador: Indicador, perspectivaId: any) : Observable<any>{
    return this.http.post<any>(`${this.urlEndPoint}/${perspectivaId}`, indicador, {headers: this.httpHeaders}).pipe(
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

  getIndicador(id): Observable<Indicador>{
    return this.http.get<Indicador>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/proyecto']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(indicador: Indicador): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${indicador.indicadorId}`, indicador, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Indicador>{
    return this.http.delete<Indicador>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
