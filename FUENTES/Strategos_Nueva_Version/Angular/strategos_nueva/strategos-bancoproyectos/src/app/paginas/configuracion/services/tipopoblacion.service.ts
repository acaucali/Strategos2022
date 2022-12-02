import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { URL_BACKEND } from 'src/app/config/config';
import { EstatusIdeas } from '../model/estatusideas';
import swal from 'sweetalert2';
import { TipoPoblacion } from '../model/tipopoblacion';

@Injectable({
  providedIn: 'root',
})
export class TipoPoblacionService {
  private urlEndPoint: string =
    URL_BACKEND + '/api/strategos/bancoproyectos/tipopoblacion';
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  public poblaciones: TipoPoblacion[];

  constructor(private http: HttpClient, private router: Router) {}

  getPoblacionesList() {
    return this.http.get(this.urlEndPoint).pipe(
      map((res) => {
        this.poblaciones = res as TipoPoblacion[];
        return this.poblaciones;
      })
    );
  }

  getPoblaciones(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint + '/page/' + page).pipe(
      map((response: any) => {
        (response.content as TipoPoblacion[]).map((estatus) => {
          return estatus;
        });
        return response;
      })
    );
  }

  create(poblacion: TipoPoblacion): Observable<any> {
    return this.http
      .post<any>(this.urlEndPoint, poblacion, { headers: this.httpHeaders })
      .pipe(
        catchError((e) => {
          if (e.status == 400) {
            return throwError(e);
          }
          console.error(e.error.mensaje);
          swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(e);
        })
      );
  }

  getPoblacion(id): Observable<TipoPoblacion> {
    return this.http.get<TipoPoblacion>(`${this.urlEndPoint}/${id}`).pipe(
      catchError((e) => {
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(poblacion: TipoPoblacion): Observable<any> {
    return this.http
      .put<any>(`${this.urlEndPoint}/${poblacion.tipoPoblacionId}`, poblacion, {
        headers: this.httpHeaders,
      })
      .pipe(
        catchError((e) => {
          if (e.status == 400) {
            return throwError(e);
          }
          console.error(e.error.mensaje);
          swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(e);
        })
      );
  }

  delete(id: number): Observable<TipoPoblacion> {
    return this.http
      .delete<TipoPoblacion>(`${this.urlEndPoint}/${id}`, {
        headers: this.httpHeaders,
      })
      .pipe(
        catchError((e) => {
          console.error(e.error.mensaje);
          swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(e);
        })
      );
  }
}
