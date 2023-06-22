import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { TiposObjetivos } from '../model/tiposobjetivos';






@Injectable({
  providedIn: 'root'
})

export class TiposObejtivosService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/tiposobjetivo';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public tiposObjetivo: TiposObjetivos[];

  constructor(private http: HttpClient, private router: Router) { }

  getTiposObjetivoList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.tiposObjetivo = res as TiposObjetivos[];
      return this.tiposObjetivo;
    }));
  }

  getTiposObjetivo(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as TiposObjetivos[]).map(tipos=>{
          return tipos;
        });
        return response;
      })
    );
  }

  create(tipo: TiposObjetivos) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, tipo, {headers: this.httpHeaders}).pipe(
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

  getTipoObjetivo(id): Observable<TiposObjetivos>{
    return this.http.get<TiposObjetivos>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(tipo: TiposObjetivos): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${tipo.tipoObjetivoId}`, tipo, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<TiposObjetivos>{
    return this.http.delete<TiposObjetivos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
