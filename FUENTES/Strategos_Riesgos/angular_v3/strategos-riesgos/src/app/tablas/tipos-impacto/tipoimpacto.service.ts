import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { TipoImpacto } from './tipoimpacto';
import swal from 'sweetalert2';
import { URL_BACKEND } from 'src/app/config/config';

@Injectable({
  providedIn: 'root'
})
export class TipoimpactoService {

  private urlEndPoint:string =URL_BACKEND+'/api/tablas/tipoimpacto';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  public impactos: TipoImpacto[]; 

  constructor(private http: HttpClient, private router: Router) { }

  getImpactosList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.impactos = res as TipoImpacto[];
      return this.impactos;
    }));
  }

  getImpactosListMapa(){
    return this.http.get(this.urlEndPoint+ '/mapa').pipe(map(res =>{
      this.impactos = res as TipoImpacto[];
      return this.impactos;
    }));
  }

  getImpactos(page: number): Observable<any> {
    //return of(CLIENTES);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as TipoImpacto[]).map(impacto=>{
          return impacto;
        });
        return response;
      })
    );
  }

  create(impacto: TipoImpacto) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, impacto, {headers: this.httpHeaders}).pipe(
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

  getImpacto(id): Observable<TipoImpacto>{
    return this.http.get<TipoImpacto>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/tiposimpacto']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(impacto: TipoImpacto): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${impacto.tipoImpactolId}`, impacto, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<TipoImpacto>{
    return this.http.delete<TipoImpacto>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
