import { Component, OnInit, Input } from '@angular/core';
import { ProcesosService } from '../procesos.service';
import { ProcesosComponent } from '../procesos.component';
import { Procesos } from '../procesos';
import swal from 'sweetalert2';
import { DocumentoService } from '../documento.service';
import { Documento } from '../documento';
import { URL_BACKEND } from 'src/app/config/config';

@Component({
  selector: 'form-proceso',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  private errores: string[];
  @Input() proceso: Procesos;
  private accion: string;
  public documento: Documento;
  private archivoSeleccionado: File;
  private urlEndPoint:string =URL_BACKEND+'/api/documentos';
  constructor(private procesosService: ProcesosService, private procesosComponent: ProcesosComponent, private documentoservice: DocumentoService) { }

  ngOnInit() {
    if(this.proceso.procesoDocumento >0){
      this.documentoservice.getDocumento(this.proceso.procesoDocumento).subscribe(response => this.documento = response);
    }
  }

  create(): void{
    this.procesosService.createProceso(this.proceso).subscribe(
      json => {
      swal.fire('Nuevo Proceso', `${json.mensaje}`, 'success');
      this.proceso= json.proceso;
      this.procesosComponent.procesoSeleccionado= json.proceso;
      this.procesosComponent.habilitarPestaña();
      this.accion = "crear";
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.procesosService.updateProceso(this.proceso).subscribe(json =>{
      swal.fire('Proceso Actualizado',  `${json.mensaje}`, 'success')
      this.procesosComponent.cargarArbol();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  cerrarVentana(){
    if(this.accion !=null && this.accion == "crear"){
      this.procesosComponent.cargarArbol();
    }else{
      this.procesosComponent.deshabilitarPestaña();
      this.procesosComponent.cerrarVentana();
    }
    
  }

  seleccionarFoto(event){
    this.archivoSeleccionado= event.target.files[0];
    console.log(this.archivoSeleccionado);
  }


  subirDocumento(){
    this.documentoservice.subirDocumento(this.proceso.procesosId, 0, this.archivoSeleccionado).subscribe(
      
      json => {
        swal.fire('Documento subido', `${json.mensaje}`, 'success');
        this.documento= json.documento;
        this.proceso.procesoDocumento= this.documento.documentoId;
        console.log(this.proceso);
        console.log(this.documento);
      },
      err =>{
        this.errores = err.error.errors as string[];
        console.error('Código error: '+err.status);
        console.error(err.error.errors);
      }

    );
  }

  eliminarDocumento(): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el documento?`,
      type: 'warning',
      showCancelButton: true,
      confirmButtonClass: 'btn btn-success',
      cancelButtonClass: 'btn btn-danger',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.documentoservice.deleteDocumento(this.proceso.procesoDocumento).subscribe(
          response =>{
            swal.fire(
              'Documento eliminado!',
              'El documento se ha eliminado con éxito',
              'success'
            )
          }
        )
        this.proceso.procesoDocumento = 0;
      }
    })
    
  }

  descargarDocumento(){
    window.open(`${this.urlEndPoint}/download/${this.proceso.procesoDocumento}`); 
  }
}
