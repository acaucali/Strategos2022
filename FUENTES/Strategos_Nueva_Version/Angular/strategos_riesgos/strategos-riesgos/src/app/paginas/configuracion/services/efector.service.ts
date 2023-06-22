import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import swal from 'sweetalert2';


import { URL_BACKEND } from 'src/app/config/config';
import { EfectoFactor } from '../model/efectoFactor';
import { FactorRiesgo } from '../model/factor';

@Injectable({
  providedIn: 'root'
})
export class efectoFactorService {

  private urlEndPoint:string =URL_BACKEND+'/api/ejercicios/efecto';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient, private router: Router) { }

  getEfectos(page: number): Observable<any> {
    //return of(PRODUCTO);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as EfectoFactor[]).map(causa=>{
          return causa;
        });
        return response;
      })
    );
  }

  create(efecto: EfectoFactor, factor: FactorRiesgo) : Observable<any>{
    return this.http.post<any>(`${this.urlEndPoint}/${factor.factorRiesgoId}`, efecto, {headers: this.httpHeaders}).pipe(
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

  getEfecto(id): Observable<EfectoFactor>{
    return this.http.get<EfectoFactor>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/factores']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(efecto: EfectoFactor): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${efecto.efectoRiesgoId}`, efecto, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<EfectoFactor>{
    return this.http.delete<EfectoFactor>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
