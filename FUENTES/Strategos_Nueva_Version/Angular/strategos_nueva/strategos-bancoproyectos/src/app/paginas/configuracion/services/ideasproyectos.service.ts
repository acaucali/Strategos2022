import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { IdeasProyectos } from '../model/ideasproyectos';





@Injectable({
  providedIn: 'root'
})

export class IdeasProyectosService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/idea';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public ideas: IdeasProyectos[];

  constructor(private http: HttpClient, private router: Router) { }

  getIdeasFiltro(orgId: any, propuestaId: any, estatusId: any, anio: string, historico: number){
    return this.http.get(`${this.urlEndPoint}/filtro/${orgId}/${propuestaId}/${estatusId}/${anio}/${historico}`).pipe(map(res =>{
      this.ideas = res as IdeasProyectos[];
      return this.ideas;
    }));
  }
  getIdeasList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.ideas = res as IdeasProyectos[];
      return this.ideas;
    }));
  }

  getIdeasListId(id: number){
    return this.http.get(`${this.urlEndPoint}/org/${id}`).pipe(map(res =>{
      this.ideas = res as IdeasProyectos[];
      return this.ideas;
    }));
  }

  getIdeas(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as IdeasProyectos[]).map(idea=>{
          return idea;
        });
        return response;
      })
    );
  }

  create(idea: IdeasProyectos) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, idea, {headers: this.httpHeaders}).pipe(
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

  getIdea(id): Observable<IdeasProyectos>{
    return this.http.get<IdeasProyectos>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(idea: IdeasProyectos): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${idea.ideaId}`, idea, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<IdeasProyectos>{
    return this.http.delete<IdeasProyectos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
