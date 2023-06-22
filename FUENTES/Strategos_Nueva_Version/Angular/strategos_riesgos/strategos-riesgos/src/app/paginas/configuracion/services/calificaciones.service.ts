import { Injectable } from '@angular/core';
import { formatDate } from '@angular/common';
import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';
import { Calificaciones } from '../model/calificaciones';
import { URL_BACKEND } from 'src/app/config/config';

@Injectable({
  providedIn:'root'
})

export class CalificacionesService {

  private urlEndPoint:string =URL_BACKEND+'/api/tablas/calificacionesriesgo';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public calificaciones: Calificaciones[];

  constructor(private http: HttpClient, private router: Router) { }

  getCalificacionesList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.calificaciones = res as Calificaciones[];
      return this.calificaciones;
    }));
  }

  getCalificaciones(page: number): Observable<any> {
    //return of(CLIENTES);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Calificaciones[]).map(calificacion=>{
          return calificacion;
        });
        return response;
      })
    );
  }

  create(calificacion: Calificaciones) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, calificacion, {headers: this.httpHeaders}).pipe(
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

  getCalificacion(id): Observable<Calificaciones>{
    return this.http.get<Calificaciones>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/calificaciones']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(calificacion: Calificaciones): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${calificacion.calificacionesRiesgoId}`, calificacion, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Calificaciones>{
    return this.http.delete<Calificaciones>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
