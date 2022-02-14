import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import swal from 'sweetalert2';
import { EfectividadControles } from './efectividad';
import { URL_BACKEND } from 'src/app/config/config';

@Injectable({
  providedIn: 'root'
})
export class EfectividadService {

  private urlEndPoint:string =URL_BACKEND+'/api/tablas/efectividad';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  public efectividades: EfectividadControles[];
  constructor(private http: HttpClient, private router: Router) { }

  getEfectividadList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.efectividades = res as EfectividadControles[];
      return this.efectividades;
    }));
  }

  create(efectividad: EfectividadControles) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, efectividad, {headers: this.httpHeaders}).pipe(
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

  getEfectividad(id): Observable<EfectividadControles>{
    return this.http.get<EfectividadControles>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/efectividad']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(efectividad: EfectividadControles): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${efectividad.efectividadId}`, efectividad, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<EfectividadControles>{
    return this.http.delete<EfectividadControles>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
