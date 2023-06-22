import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { URL_BACKEND } from 'src/app/config/config';
import { EstatusIdeas } from '../model/estatusideas';
import swal from 'sweetalert2';
import { EvaluacionIdeasDetalle } from '../model/evaluacionideasdetalle';
import { DatoIdea } from '../model-util/DatoIdea';
import { IdeasEvaluadas } from '../model/ideasevaluadas';
import { DatoMedicion } from '../model-util/DatoMedicion';


@Injectable({
  providedIn: 'root'
})

export class EvaluacionIdeasDetalleService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/evaluaciondetalle';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public evaluacionDetalle: EvaluacionIdeasDetalle[];
  public encabezados: DatoIdea[];
  public datos: DatoMedicion[];
  public ideas: number[];

  constructor(private http: HttpClient, private router: Router) { }

  getEncabezadosList(id){
    return this.http.get(`${this.urlEndPoint}/encabezados/${id}`).pipe(map(res =>{
      this.encabezados = res as DatoIdea[];
      return this.encabezados;
    }));
  }
  
  getMedicionesList(id){
    return this.http.get(`${this.urlEndPoint}/datosmediciones/${id}`).pipe(map(res =>{
      this.datos = res as DatoMedicion[];
      return this.datos;
    }));
  }

  getEvaluacionesList(id){
    return this.http.get(`${this.urlEndPoint}/ideasevaluadas/${id}`).pipe(map(res =>{
      this.ideas = res as number[];
      return this.ideas;
    }));
  }

  getEvaluacionDetalleList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.evaluacionDetalle = res as EvaluacionIdeasDetalle[];
      return this.evaluacionDetalle;
    }));
  }

  getDetalle(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as EvaluacionIdeasDetalle[]).map(detalle=>{
          return detalle;
        });
        return response;
      })
    );
  }

  create(evaluacion: EvaluacionIdeasDetalle) : Observable<any>{
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

  getEvaluacion(id): Observable<EvaluacionIdeasDetalle>{
    return this.http.get<EvaluacionIdeasDetalle>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/evaluaciondetalle']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(evaluacion: EvaluacionIdeasDetalle): Observable<any>{
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

  delete(id: number): Observable<EvaluacionIdeasDetalle>{
    return this.http.delete<EvaluacionIdeasDetalle>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  registrarMediciones(mediciones: Array<DatoMedicion>, evaId: any): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/mediciones/${evaId}`, mediciones, {headers: this.httpHeaders }).pipe(
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

}

