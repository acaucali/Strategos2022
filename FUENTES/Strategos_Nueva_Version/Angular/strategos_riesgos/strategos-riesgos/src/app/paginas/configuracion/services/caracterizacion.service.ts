import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import swal from 'sweetalert2';

import { URL_BACKEND } from 'src/app/config/config';
import { Caracterizacion } from '../model/caracterizacion';
import { Procesos } from '../model/procesos';

@Injectable({
  providedIn: 'root'
})
export class CaracterizacionService {

  private urlEndPoint:string =URL_BACKEND+ '/api/procesos/caracterizacion';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient, private router: Router) { }

  getProcedimientos(page: number): Observable<any> {
    //return of(PRODUCTO);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Caracterizacion[]).map(procedimiento=>{
          return procedimiento;
        });
        return response;
      })
    );
  }

  create(procedimiento: Caracterizacion, proceso: Procesos) : Observable<any>{
    console.log(procedimiento);
    console.log(proceso);
    procedimiento.procedimientoDocumento=0;
    return this.http.post<any>(`${this.urlEndPoint}/${proceso.procesosId}`, procedimiento, {headers: this.httpHeaders}).pipe(
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

  getProcedimiento(id): Observable<Caracterizacion>{
    return this.http.get<Caracterizacion>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/procesos']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(procedimiento: Caracterizacion): Observable<any>{

    return this.http.put<any>(`${this.urlEndPoint}/${procedimiento.procedimientoId}`, procedimiento, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Caracterizacion>{
    return this.http.delete<Caracterizacion>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
