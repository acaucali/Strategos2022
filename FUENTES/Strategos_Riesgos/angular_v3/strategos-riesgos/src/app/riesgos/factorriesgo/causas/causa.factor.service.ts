import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import swal from 'sweetalert2';
import { CausaFactor } from './causaFactor';
import { FactorRiesgo } from '../factor';
import { URL_BACKEND } from 'src/app/config/config';

@Injectable({
  providedIn: 'root'
})
export class CausaFactorService {

  private urlEndPoint:string =URL_BACKEND+'/api/ejercicios/causa';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public causasNombre: string[];

  constructor(private http: HttpClient, private router: Router) { }

  getCausas(page: number): Observable<any> {
    //return of(PRODUCTO);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as CausaFactor[]).map(causa=>{
          return causa;
        });
        return response;
      })
    );
  }

  create(causa: CausaFactor, factor: FactorRiesgo) : Observable<any>{
    return this.http.post<any>(`${this.urlEndPoint}/${factor.factorRiesgoId}`, causa, {headers: this.httpHeaders}).pipe(
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

  getCausa(id): Observable<CausaFactor>{
    return this.http.get<CausaFactor>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/factores']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(causa: CausaFactor): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${causa.causaRiesgoId}`, causa, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<CausaFactor>{
    return this.http.delete<CausaFactor>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getCausasGrafico(){
    return this.http.get(`${this.urlEndPoint}/grafico`).pipe(map(res => {
      this.causasNombre = res as string[];
      return this.causasNombre;
    }));
    
  }
}
