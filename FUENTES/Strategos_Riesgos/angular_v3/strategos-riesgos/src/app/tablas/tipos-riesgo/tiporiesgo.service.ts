import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { TipoRiesgo } from './tiporiesgo';
import swal from 'sweetalert2';
import { URL_BACKEND } from 'src/app/config/config';

@Injectable({
  providedIn: 'root'
})
export class TiporiesgoService {

  private urlEndPoint:string =URL_BACKEND+'/api/tablas/tiporiesgo';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public riesgos: TipoRiesgo[];

  constructor(private http: HttpClient, private router: Router) { }

  getRiesgos(page: number): Observable<any> {
    //return of(CLIENTES);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as TipoRiesgo[]).map(riesgo=>{
          return riesgo;
        });
        return response;
      })
    );
  }

  getRiesgosList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.riesgos = res as TipoRiesgo[];
      return this.riesgos;
    }));
  }

  create(riesgo: TipoRiesgo) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, riesgo, {headers: this.httpHeaders}).pipe(
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

  getRiesgo(id): Observable<TipoRiesgo>{
    return this.http.get<TipoRiesgo>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/tiporiesgo']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(riesgo: TipoRiesgo): Observable<any>{
    console.log(riesgo)
    return this.http.put<any>(`${this.urlEndPoint}/${riesgo.tipoRiesgoId}`, riesgo, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<TipoRiesgo>{
    return this.http.delete<TipoRiesgo>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
