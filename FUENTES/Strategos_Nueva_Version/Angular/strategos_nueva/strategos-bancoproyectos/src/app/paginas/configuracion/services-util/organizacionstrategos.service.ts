import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { OrganizacionStrategos } from '../model-util/organizacionstrategos';




@Injectable({
  providedIn: 'root'
})

export class OrganizacionStrategosService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/organizacion';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public organizaciones: OrganizacionStrategos[];

  constructor(private http: HttpClient, private router: Router) { }

  getOrganizacionesList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.organizaciones = res as OrganizacionStrategos[];
      return this.organizaciones;
    }));
  }

  getOrganizaciones(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as OrganizacionStrategos[]).map(org=>{
          return org;
        });
        return response;
      })
    );
  }

  create(org: OrganizacionStrategos) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, org, {headers: this.httpHeaders}).pipe(
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

  getOrganizacion(id): Observable<OrganizacionStrategos>{
    return this.http.get<OrganizacionStrategos>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(org: OrganizacionStrategos): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${org.organizacionId}`, org, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<OrganizacionStrategos>{
    return this.http.delete<OrganizacionStrategos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
