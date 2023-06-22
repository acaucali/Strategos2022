import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { TiposPropuestas } from '../model/tipospropuestas';
import { Plan } from '../model/plan';
import { ProyectosPlan } from '../model/proyectosplan';
import { Perspectiva } from '../model/perspectiva';
import { Arbol } from '../model-util/arbol';



@Injectable({
  providedIn: 'root'
})

export class PerspectivaService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/perspectiva';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public perspectivas: Perspectiva[];
  public arbol: Arbol[];
  

  constructor(private http: HttpClient, private router: Router) { }

  getPerspectivasList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.perspectivas = res as Perspectiva[];
      return this.perspectivas;
    }));
  }

  getPerspectivasArbol(planId: any){
    return this.http.get(`${this.urlEndPoint}/arbol/${planId}`).pipe(map(res => {
      this.arbol = res as Arbol[];
        return this.arbol;
    }));
    
  }

  getPerspectivaPage(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Perspectiva[]).map(tipos=>{
          return tipos;
        });
        return response;
      })
    );
  }

  create(perspectiva: Perspectiva, planId: any, proyectoId: any) : Observable<any>{
    return this.http.post<any>(`${this.urlEndPoint}/${planId}/${proyectoId}`, perspectiva, {headers: this.httpHeaders}).pipe(
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

  getPerspectiva(id): Observable<Perspectiva>{
    return this.http.get<Perspectiva>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/proyecto']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(perspectiva: Perspectiva): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${perspectiva.perspectivaId}`, perspectiva, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Perspectiva>{
    return this.http.delete<Perspectiva>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
