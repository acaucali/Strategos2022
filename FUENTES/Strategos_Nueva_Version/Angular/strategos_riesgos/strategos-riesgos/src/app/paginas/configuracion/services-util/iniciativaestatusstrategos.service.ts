import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { OrganizacionStrategos } from '../model-util/organizacionstrategos';
import { IniciativaEstatusStrategos } from '../model-util/IniciativaEstatusStrategos';




@Injectable({
  providedIn: 'root'
})

export class IniciativaEstatusStrategosService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/iniciativaestatus';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public estatus: IniciativaEstatusStrategos[];

  constructor(private http: HttpClient, private router: Router) { }

  getIniciativaEstatusList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.estatus = res as IniciativaEstatusStrategos[];
      return this.estatus;
    }));
  }

  getIniciativaEstatus(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as IniciativaEstatusStrategos[]).map(ini=>{
          return ini;
        });
        return response;
      })
    );
  }

  create(ini: IniciativaEstatusStrategos) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, ini, {headers: this.httpHeaders}).pipe(
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

  getIniciativaEstatu(id): Observable<IniciativaEstatusStrategos>{
    return this.http.get<IniciativaEstatusStrategos>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(ini: IniciativaEstatusStrategos): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${ini.id}`, ini, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<IniciativaEstatusStrategos>{
    return this.http.delete<IniciativaEstatusStrategos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
