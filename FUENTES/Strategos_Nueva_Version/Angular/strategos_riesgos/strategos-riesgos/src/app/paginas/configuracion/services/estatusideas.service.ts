import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { URL_BACKEND } from 'src/app/config/config';
import { EstatusIdeas } from '../model/estatusideas';
import swal from 'sweetalert2';


@Injectable({
  providedIn: 'root'
})

export class EstatusIdeaService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/estatus';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public estatus: EstatusIdeas[];

  constructor(private http: HttpClient, private router: Router) { }

  getEstatusList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.estatus = res as EstatusIdeas[];
      return this.estatus;
    }));
  }

  getEstatus(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as EstatusIdeas[]).map(estatus=>{
          return estatus;
        });
        return response;
      })
    );
  }

  create(estatus: EstatusIdeas) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, estatus, {headers: this.httpHeaders}).pipe(
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

  getCriterio(id): Observable<EstatusIdeas>{
    return this.http.get<EstatusIdeas>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(estatus: EstatusIdeas): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${estatus.estatusIdeaId}`, estatus, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<EstatusIdeas>{
    return this.http.delete<EstatusIdeas>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}

