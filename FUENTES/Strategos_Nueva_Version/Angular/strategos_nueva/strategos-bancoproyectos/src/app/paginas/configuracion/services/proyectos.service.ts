import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { URL_BACKEND } from 'src/app/config/config';
import { EstatusIdeas } from '../model/estatusideas';
import swal from 'sweetalert2';
import { Proyectos } from '../model/proyectos';


@Injectable({
  providedIn: 'root'
})

export class ProyectoService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/proyecto';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public proyectos: Proyectos[];

  constructor(private http: HttpClient, private router: Router) { }

  getProyectosList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.proyectos = res as Proyectos[];
      return this.proyectos;
    }));
  }

  getProyectosFiltro(orgId: any, tipoId: any, estatusId: any, anio: string, historico: number){
    return this.http.get(`${this.urlEndPoint}/filtro/${orgId}/${tipoId}/${estatusId}/${anio}/${historico}`).pipe(map(res =>{
      this.proyectos = res as Proyectos[];
      return this.proyectos;
    }));
  }

  getProyectosListId(id: number){
    return this.http.get(`${this.urlEndPoint}/org/${id}`).pipe(map(res =>{
      this.proyectos = res as Proyectos[];
      return this.proyectos;
    }));
  }

  getProyectos(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Proyectos[]).map(estatus=>{
          return estatus;
        });
        return response;
      })
    );
  }

  create(proyectos: Proyectos) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, proyectos, {headers: this.httpHeaders}).pipe(
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

  getProyecto(id): Observable<Proyectos>{
    return this.http.get<Proyectos>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/proyectos']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(proyectos: Proyectos): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${proyectos.proyectoId}`, proyectos, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Proyectos>{
    return this.http.delete<Proyectos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}

