import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import swal from 'sweetalert2';
import { URL_BACKEND } from 'src/app/config/config';
import { Causas } from '../model/causas';

@Injectable({
  providedIn: 'root'
})
export class CausasService { 

  private urlEndPoint:string =URL_BACKEND+'/api/tablas/causariesgo';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  public causas: Causas[];
  constructor(private http: HttpClient, private router: Router) { }

  getCausasList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.causas = res as Causas[];
      return this.causas;
    }));
  }

  getCausas(page: number): Observable<any> {
    //return of(CLIENTES);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Causas[]).map(causa=>{
          return causa;
        });
        return response;
      })
    );
  }

  create(causa: Causas) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, causa, {headers: this.httpHeaders}).pipe(
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

  getCausa(id): Observable<Causas>{
    return this.http.get<Causas>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/causas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(causa: Causas): Observable<any>{
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

  delete(id: number): Observable<Causas>{
    return this.http.delete<Causas>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
