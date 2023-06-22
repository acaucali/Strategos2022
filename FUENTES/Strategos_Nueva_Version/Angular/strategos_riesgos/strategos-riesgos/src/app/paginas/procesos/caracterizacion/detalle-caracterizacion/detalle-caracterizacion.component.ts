import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { URL_BACKEND } from 'src/app/config/config';
import { Caracterizacion } from 'src/app/paginas/configuracion/model/caracterizacion';
import { Documento } from 'src/app/paginas/configuracion/model/documento';
import { Procesos } from 'src/app/paginas/configuracion/model/procesos';
import { CaracterizacionService } from 'src/app/paginas/configuracion/services/caracterizacion.service';
import { DocumentoService } from 'src/app/paginas/configuracion/services/documento.service';
import swal from 'sweetalert2';
import { CaracterizacionComponent } from '../caracterizacion.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'detalle-procedimiento',
  templateUrl: './detalle-caracterizacion.component.html',
  styleUrls: ['./detalle-caracterizacion.component.css']
})
export class DetalleCaracterizacionComponent implements OnInit {

  private errores: string[];
 

  @Input() caracterizacion: Caracterizacion;

  @Input() proceso: Procesos;

  public titulo: string = "Datos del procedimiento";

  public documento: Documento;
  private archivoSeleccionado: File;
  private urlEndPoint:string =URL_BACKEND+'/api/documentos';
  constructor(private caracterizacionService: CaracterizacionService, private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private procedimientoComponent: CaracterizacionComponent, private documentoservice: DocumentoService
   ) {   }

  ngOnInit() {
    if(this.proceso.procesoDocumento >0){
      this.documentoservice.getDocumento(this.proceso.procesoDocumento).subscribe(response => this.documento = response);
    }
  }

  
  create(): void{
    this.caracterizacionService.create(this.caracterizacion, this.proceso).subscribe(
      json => {
      swal.fire('Nuevo Procedimiento', `${json.mensaje}`, 'success');
      this.cerrarModal();  
      this.procedimientoComponent.procedimientos= null;
      this.procedimientoComponent.getProcedimientos();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }
  
  update(): void{
    this.caracterizacionService.update(this.caracterizacion).subscribe(json =>{
      swal.fire('Procedimiento Actualizado',  `${json.mensaje}`, 'success')
      this.cerrarModal();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }


  cerrarModal(){
    this.modalservice.cerrarModal();
  }

  seleccionarFoto(event){
    this.archivoSeleccionado= event.target.files[0];
    console.log(this.archivoSeleccionado);
  }


  subirDocumento(){
    this.documentoservice.subirDocumento(this.caracterizacion.procedimientoId, 1, this.archivoSeleccionado).subscribe(
      
      json => {
        swal.fire('Documento subido', `${json.mensaje}`, 'success');
        this.documento= json.documento;
        this.caracterizacion.procedimientoDocumento= this.documento.documentoId;
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
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.documentoservice.deleteDocumento(this.caracterizacion.procedimientoDocumento).subscribe(
          response =>{
            swal.fire(
              'Documento eliminado!',
              'El documento se ha eliminado con éxito',
              'success'
            )
          }
        )
        this.caracterizacion.procedimientoDocumento = 0;
      }
    })
    
  }

  descargarDocumento(){
    window.open(`${this.urlEndPoint}/download/${this.caracterizacion.procedimientoDocumento}`); 
  }

}
