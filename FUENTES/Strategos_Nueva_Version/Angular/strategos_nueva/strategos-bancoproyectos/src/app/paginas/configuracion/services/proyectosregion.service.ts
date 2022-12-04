import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { URL_BACKEND } from 'src/app/config/config';
import { EstatusIdeas } from '../model/estatusideas';
import swal from 'sweetalert2';
import { ProyectosRegion } from '../model/proyectosregion';

@Injectable({
  providedIn: 'root',
})
export class ProyectosregionService {
  private urlEndPoint: string =
    URL_BACKEND + '/api/strategos/bancoproyectos/proyectosRegion';
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient, private router: Router) {}

  delete(id: number): Observable<ProyectosRegion> {
    return this.http
      .delete<ProyectosRegion>(`${this.urlEndPoint}/${id}`, {
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
