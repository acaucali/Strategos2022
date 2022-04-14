import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { Idea } from './ideas';


@Injectable()

export class IdeaService {

  private urlEndPoint:string =URL_BACKEND+'/api/pruebadesarrollo/transaccion';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public ideas: Idea[];

  constructor(private http: HttpClient, private router: Router) { }

  getIdeasList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.ideas = res as Idea[];
      return this.ideas;
    }));
  }

  getIdeas(page: number): Observable<any> {
    //return of(ideas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Idea[]).map(idea=>{
          return idea;
        });
        return response;
      })
    );
  }

  create(idea: Idea) : Observable<any>{
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

  getIdea(id): Observable<Idea>{
    return this.http.get<Idea>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/transacciones']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(transaccion: Idea): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${transaccion.transaccionId}`, transaccion, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Transaccion>{
    return this.http.delete<Transaccion>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
