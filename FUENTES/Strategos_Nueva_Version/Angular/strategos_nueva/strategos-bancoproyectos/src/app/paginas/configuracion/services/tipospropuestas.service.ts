import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { TiposPropuestas } from '../model/tipospropuestas';



@Injectable({
  providedIn: 'root'
})

export class TiposPropuestaService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/tipopropuesta';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public tiposPropuesta: TiposPropuestas[];

  constructor(private http: HttpClient, private router: Router) { }

  getTiposPropuestaList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.tiposPropuesta = res as TiposPropuestas[];
      return this.tiposPropuesta;
    }));
  }

  getTtiposPropuesta(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as TiposPropuestas[]).map(tipos=>{
          return tipos;
        });
        return response;
      })
    );
  }

  create(tipo: TiposPropuestas) : Observable<any>{
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

  getTipoPropuesta(id): Observable<TiposPropuestas>{
    return this.http.get<TiposPropuestas>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(tipo: TiposPropuestas): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${tipo.tipoPropuestaId}`, tipo, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<TiposPropuestas>{
    return this.http.delete<TiposPropuestas>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
