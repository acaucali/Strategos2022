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
import { Unidad } from '../model/unidad';



@Injectable({
  providedIn: 'root'
})

export class UnidadService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/unidad';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public unidades: Unidad[];
   

  constructor(private http: HttpClient, private router: Router) { }

  getUnidadesList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.unidades = res as Unidad[];
      return this.unidades;
    }));
  }

  getUnidadPage(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Unidad[]).map(uni=>{
          return uni;
        });
        return response;
      })
    );
  }

  create(unidad: Unidad) : Observable<any>{
    return this.http.post<any>(`${this.urlEndPoint}`, unidad, {headers: this.httpHeaders}).pipe(
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

  getUnidad(id): Observable<Unidad>{
    return this.http.get<Unidad>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/proyecto']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(unidad: Unidad): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${unidad.unidadId}`, unidad, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Unidad>{
    return this.http.delete<Unidad>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
