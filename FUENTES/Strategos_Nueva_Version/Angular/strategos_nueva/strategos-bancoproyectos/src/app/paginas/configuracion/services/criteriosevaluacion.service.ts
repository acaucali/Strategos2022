import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { CriteriosEvaluacion } from '../model/criteriosevaluacion';




@Injectable({
  providedIn: 'root'
})

export class CriteriosEvaluacionService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/criterios';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public criterios: CriteriosEvaluacion[];

  constructor(private http: HttpClient, private router: Router) { }

  getCriteriosList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.criterios = res as CriteriosEvaluacion[];
      return this.criterios;
    }));
  }

  getCriterios(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as CriteriosEvaluacion[]).map(criterio=>{
          return criterio;
        });
        return response;
      })
    );
  }

  create(criterio: CriteriosEvaluacion) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, criterio, {headers: this.httpHeaders}).pipe(
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

  getCriterio(id): Observable<CriteriosEvaluacion>{
    return this.http.get<CriteriosEvaluacion>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(criterio: CriteriosEvaluacion): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${criterio.criterioId}`, criterio, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<CriteriosEvaluacion>{
    return this.http.delete<CriteriosEvaluacion>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
