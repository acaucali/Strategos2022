import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { IdeasDocumentosAnexos } from '../model/ideasdocumentosanexos';




@Injectable({
  providedIn: 'root'
})

export class IdeasDocumentosService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/documento';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public documentos: IdeasDocumentosAnexos[];

  constructor(private http: HttpClient, private router: Router) { }

  getDocumentosList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.documentos = res as IdeasDocumentosAnexos[];
      return this.documentos;
    }));
  }

  getDocumentos(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as IdeasDocumentosAnexos[]).map(documento=>{
          return documento;
        });
        return response;
      })
    );
  }

  create(documento: IdeasDocumentosAnexos) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, documento, {headers: this.httpHeaders}).pipe(
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

  getDocumento(id): Observable<IdeasDocumentosAnexos>{
    return this.http.get<IdeasDocumentosAnexos>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(documento: IdeasDocumentosAnexos): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${documento.documentoId}`, documento, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<IdeasDocumentosAnexos>{
    return this.http.delete<IdeasDocumentosAnexos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
