import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import swal from 'sweetalert2';

import { URL_BACKEND } from 'src/app/config/config';
import { Efectos } from '../model/efectos';

@Injectable({
  providedIn: 'root'
})
export class EfectosService {

  private urlEndPoint:string =URL_BACKEND+'/api/tablas/efectos';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public efectos: Efectos[];

  constructor(private http: HttpClient, private router: Router) { }

  getEfectosList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.efectos = res as Efectos[];
      return this.efectos;
    }));
  }

  create(efecto: Efectos) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, efecto, {headers: this.httpHeaders}).pipe(
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

  getEfecto(id): Observable<Efectos>{
    return this.http.get<Efectos>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/efectos']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(efecto: Efectos): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${efecto.efectoId}`, efecto, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Efectos>{
    return this.http.delete<Efectos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
