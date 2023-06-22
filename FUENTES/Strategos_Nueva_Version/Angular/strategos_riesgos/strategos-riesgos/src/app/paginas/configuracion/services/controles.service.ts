import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import swal from 'sweetalert2';
import { URL_BACKEND } from 'src/app/config/config';
import { Controles } from '../model/controles';

@Injectable({
  providedIn: 'root'
})
export class ControlesService {

  private urlEndPoint:string =URL_BACKEND+'/api/tablas/controles';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public controles: Controles[];

  constructor(private http: HttpClient, private router: Router) { }

  getControlesList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.controles = res as Controles[];
      return this.controles;
    }));
  }

  getControlesListProceso(procesoId: number){
    return this.http.get(this.urlEndPoint+ '/proceso/'+procesoId).pipe(map(res =>{
      this.controles = res as Controles[];
      return this.controles;
    }));
  }

  getControles(page: number): Observable<any> {
    //return of(CLIENTES);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Controles[]).map(control=>{
          return control;
        });
        return response;
      })
    );
  }

  create(control: Controles) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, control, {headers: this.httpHeaders}).pipe(
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

  getControl(id): Observable<Controles>{
    return this.http.get<Controles>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/controles']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(control: Controles): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${control.controlId}`, control, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Controles>{
    return this.http.delete<Controles>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
