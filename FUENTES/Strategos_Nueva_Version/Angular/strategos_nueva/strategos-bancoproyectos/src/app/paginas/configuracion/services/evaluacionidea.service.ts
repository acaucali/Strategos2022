import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { URL_BACKEND } from 'src/app/config/config';
import { EstatusIdeas } from '../model/estatusideas';
import swal from 'sweetalert2';
import { EvaluacionIdeas } from '../model/evaluacionideas';
import { IdeasProyectos } from '../model/ideasproyectos';


@Injectable({
  providedIn: 'root'
})

export class EvaluacionIdeasService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/evaluacion';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public evaluaciones: EvaluacionIdeas[];
  public ideas: IdeasProyectos[];

  constructor(private http: HttpClient, private router: Router) { }

  getEvaluacionesList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.evaluaciones = res as EvaluacionIdeas[];
      return this.evaluaciones;
    }));
  }

  getIdeasList(id){
    return this.http.get(`${this.urlEndPoint}/ideas/${id}`).pipe(map(res =>{
      this.ideas = res as IdeasProyectos[];
      return this.ideas;
    }));
  }

  getEvaluacionPage(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as EvaluacionIdeas[]).map(estatus=>{
          return estatus;
        });
        return response;
      })
    );
  }

  create(evaluacion: EvaluacionIdeas) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, evaluacion, {headers: this.httpHeaders}).pipe(
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

  getEvaluacion(id): Observable<EvaluacionIdeas>{
    return this.http.get<EvaluacionIdeas>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/evaluacion']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(evaluacion: EvaluacionIdeas): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${evaluacion.evaluacionId}`, evaluacion, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<EvaluacionIdeas>{
    return this.http.delete<EvaluacionIdeas>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}

