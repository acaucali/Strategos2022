import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import swal from 'sweetalert2';


import { URL_BACKEND } from 'src/app/config/config';
import { ControlFactor } from '../model/controlFactor';
import { FactorRiesgo } from '../model/factor';

@Injectable({
  providedIn: 'root'
})
export class ControlFactorService {

  private urlEndPoint:string =URL_BACKEND+'/api/ejercicios/control';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient, private router: Router) { }

  getControles(page: number): Observable<any> {
    //return of(PRODUCTO);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as ControlFactor[]).map(control=>{
          return control;
        });
        return response;
      })
    );
  }

  create(control: ControlFactor, factor: FactorRiesgo) : Observable<any>{
    return this.http.post<any>(`${this.urlEndPoint}/${factor.factorRiesgoId}`, control, {headers: this.httpHeaders}).pipe(
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

  getControl(id): Observable<ControlFactor>{
    return this.http.get<ControlFactor>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/controlesfac']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(control: ControlFactor): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${control.controlRiesgoId}`, control, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<ControlFactor>{
    return this.http.delete<ControlFactor>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
