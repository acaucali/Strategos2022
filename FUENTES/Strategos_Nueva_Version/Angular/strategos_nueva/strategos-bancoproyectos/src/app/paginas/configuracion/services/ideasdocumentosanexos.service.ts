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

  subirDocumento(origenId, documento, archivo: File) : Observable<any>{

    let formData = new FormData();
    formData.append("archivo", archivo);
    formData.append("id", origenId);
    formData.append("titulo", documento.tituloDocumento);
    formData.append("descripcion", documento.descripcion);

    return this.http.post<any>(`${this.urlEndPoint}/upload`, formData).pipe(
      catchError(e=>{
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
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

  
  
  deleteDocumento(id: number): Observable<IdeasDocumentosAnexos>{
    return this.http.delete<IdeasDocumentosAnexos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
