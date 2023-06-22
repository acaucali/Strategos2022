import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { Responsable } from '../model/responsable';





@Injectable({
  providedIn: 'root'
})

export class ResponsableService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/responsable';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public responsables: Responsable[];

  constructor(private http: HttpClient, private router: Router) { }

  getResponsablesList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.responsables = res as Responsable[];
      return this.responsables;
    }));
  }

  getResponsables(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Responsable[]).map(org=>{
          return org;
        });
        return response;
      })
    );
  }

  create(res: Responsable) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, res, {headers: this.httpHeaders}).pipe(
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

  getResponsable(id): Observable<Responsable>{
    return this.http.get<Responsable>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(res: Responsable): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${res.responsableId}`, res, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Responsable>{
    return this.http.delete<Responsable>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
