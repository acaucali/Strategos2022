import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { Indicador } from '../model/indicador';
import { Actividad } from '../model/actividad';

@Injectable({
    providedIn: 'root'
  })
  
  export class ActividadService {
  
    private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/actividad';
    private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    public actividades: Actividad[];
     
  
    constructor(private http: HttpClient, private router: Router) { }
  
    getActividadesList(){
      return this.http.get(this.urlEndPoint).pipe(map(res =>{
        this.actividades = res as Actividad[];
        return this.actividades;
      }));
    }
  
    getActividadesProyecto(id: number){
      return this.http.get(`${this.urlEndPoint}/tarea/${id}`).pipe(map(res =>{
        this.actividades = res as Actividad[];
        return this.actividades;
      }));
    }
  
    getIndicadorPage(page: number): Observable<any> {
      //return of(tarjetas);
      return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
        map((response: any) => {
          (response.content as Actividad[]).map(ind=>{
            return ind;
          });
          return response;
        })
      );
    }
  
    create(actividad: Actividad, actividadId: any, objetivoId: any, orgId: any) : Observable<any>{
      return this.http.post<any>(`${this.urlEndPoint}/${actividadId}/${objetivoId}/${orgId}`, actividad, {headers: this.httpHeaders}).pipe(
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
  
    getActividad(id): Observable<Actividad>{
      return this.http.get<Actividad>(`${this.urlEndPoint}/${id}`).pipe(
        catchError(e=>{
          this.router.navigate(['/proyecto']);
          console.error(e.error.mensaje);
          swal.fire('Error al editar', e.error.mensaje, 'error');
          return throwError(e);
        })
      );
    }
  
    update(actividad: Actividad): Observable<any>{
      return this.http.put<any>(`${this.urlEndPoint}/${actividad.actividadId}`, actividad, {headers: this.httpHeaders }).pipe(
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
  
    delete(id: number): Observable<Actividad>{
      return this.http.delete<Actividad>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
        catchError(e =>{
          console.error(e.error.mensaje);
          swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(e);
        })
      );
    }
  
  
  }
  