import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import swal from 'sweetalert2';
import { URL_BACKEND } from 'src/app/config/config';
import { Respuesta } from '../model/respuesta';

@Injectable({
  providedIn: 'root'
})
export class RespuestaService {

  private urlEndPoint:string =URL_BACKEND+'/api/tablas/respuesta';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  public respuestas: Respuesta[];

  constructor(private http: HttpClient, private router: Router) { }

  getRespuestasList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.respuestas = res as Respuesta[];
      return this.respuestas;
    }));
  }


  getRespuestas(page: number): Observable<any> {
    //return of(CLIENTES);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Respuesta[]).map(respuesta=>{
          return respuesta;
        });
        return response;
      })
    );
  }

  create(respuesta: Respuesta) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, respuesta, {headers: this.httpHeaders}).pipe(
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

  getRespuesta(id): Observable<Respuesta>{
    return this.http.get<Respuesta>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/respuesta']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(respuesta: Respuesta): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${respuesta.respuestaId}`, respuesta, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Respuesta>{
    return this.http.delete<Respuesta>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
