import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { OrganizacionStrategos } from '../model-util/organizacionstrategos';
import { MetodologiaStrategos } from '../model-util/MetodologiaStrategos';




@Injectable({
  providedIn: 'root'
})

export class MetodologiaStrategosService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/metodologia';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public metodologias: MetodologiaStrategos[];

  constructor(private http: HttpClient, private router: Router) { }

  getMetodologiasList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.metodologias = res as MetodologiaStrategos[];
      return this.metodologias;
    }));
  }

  getMetodologias(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as MetodologiaStrategos[]).map(met=>{
          return met;
        });
        return response;
      })
    );
  }

  create(met: MetodologiaStrategos) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, met, {headers: this.httpHeaders}).pipe(
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

  getMetodologia(id): Observable<MetodologiaStrategos>{
    return this.http.get<MetodologiaStrategos>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(met: MetodologiaStrategos): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${met.metodologiaId}`, met, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<MetodologiaStrategos>{
    return this.http.delete<MetodologiaStrategos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
