import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import swal from 'sweetalert2';
import { URL_BACKEND } from 'src/app/config/config';
import { Probabilidad } from '../model/probabilidad';

@Injectable({
  providedIn: 'root'
})
export class ProbabilidadService {

  private urlEndPoint:string =URL_BACKEND+'/api/tablas/probabilidad';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  public probabilidades: Probabilidad[];

  constructor(private http: HttpClient, private router: Router) { }

  getProbabilidadesList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.probabilidades = res as Probabilidad[];
      return this.probabilidades;
    }));
  } 

  getProbabilidadesListMapa(){
    return this.http.get(this.urlEndPoint+ '/mapa').pipe(map(res =>{
      this.probabilidades = res as Probabilidad[];
      return this.probabilidades;
    }));
  } 

  getProbabilidades(page: number): Observable<any> {
    //return of(CLIENTES);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Probabilidad[]).map(probabilidad=>{
          return probabilidad;
        });
        return response;
      })
    );
  }

  create(probabilidad: Probabilidad) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, probabilidad, {headers: this.httpHeaders}).pipe(
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

  getProbabilidad(id): Observable<Probabilidad>{
    return this.http.get<Probabilidad>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/probabilidad']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(probabilidad: Probabilidad): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${probabilidad.probabilidadId}`, probabilidad, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Probabilidad>{
    return this.http.delete<Probabilidad>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
