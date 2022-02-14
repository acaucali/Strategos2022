import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { MacroProcesos } from './macroprocesos';
import { URL_BACKEND } from 'src/app/config/config';

@Injectable({
  providedIn: 'root'
})
export class MacroprocesosService {

  private urlEndPoint:string =URL_BACKEND+'/api/tablas/macroproceso';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  public macros: MacroProcesos[];

  constructor(private http: HttpClient, private router: Router) { }

  getMacrosList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.macros = res as MacroProcesos[];
      return this.macros;
    }));
  }

  getMacros(page: number): Observable<any> {
    //return of(MacroProcesos);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as MacroProcesos[]).map(macro=>{
          return macro;
        });
        return response;
      })
    );
  }

  create(macro: MacroProcesos) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, macro, {headers: this.httpHeaders}).pipe(
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

  getMacro(id): Observable<MacroProcesos>{
    return this.http.get<MacroProcesos>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/macros']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(macro: MacroProcesos): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${macro.macroProcesoId}`, macro, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<MacroProcesos>{
    return this.http.delete<MacroProcesos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
