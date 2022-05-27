import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { URL_BACKEND } from 'src/app/config/config';
import swal from 'sweetalert2';
import { OrganizacionStrategos } from '../../configuracion/model-util/organizacionstrategos';
import { EstatusIdeas } from '../../configuracion/model/estatusideas';
import { IdeasDocumentosAnexos } from '../../configuracion/model/ideasdocumentosanexos';
import { IdeasProyectos } from '../../configuracion/model/ideasproyectos';
import { TiposObjetivos } from '../../configuracion/model/tiposobjetivos';
import { TiposPropuestas } from '../../configuracion/model/tipospropuestas';
import { OrganizacionStrategosService } from '../../configuracion/services-util/organizacionstrategos.service';
import { EstatusIdeaService } from '../../configuracion/services/estatusideas.service';
import { IdeasDocumentosService } from '../../configuracion/services/ideasdocumentosanexos.service';
import { IdeasProyectosService } from '../../configuracion/services/ideasproyectos.service';
import { TiposObejtivosService } from '../../configuracion/services/tiposobjetivos.service';
import { TiposPropuestaService } from '../../configuracion/services/tipospropuestas.service';
import { GestionideasComponent } from '../gestionideas.component';
import { ModalService } from './modal.service';


@Component({
  selector: 'detallegestion',
  templateUrl: './detallegestion.component.html',
  styleUrls: ['./detallegestion.component.css']
})
export class DetallegestionComponent implements OnInit {

  private errores: string[];
  @Input() idea: IdeasProyectos = new IdeasProyectos;

  propuestas: TiposPropuestas[];
  objetivos: TiposObjetivos[];
  estatus: EstatusIdeas[];
  organizaciones: OrganizacionStrategos[];
  organizacionId: any;
  public isAdmin: boolean = false;
  public documento: IdeasDocumentosAnexos = new IdeasDocumentosAnexos;
  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/documento/';
  private archivoSeleccionado: File;
  
  titulo: string = "Datos de la idea";
  constructor(public modalservice: ModalService, private router: Router, private estatusService: EstatusIdeaService, private propuestasService: TiposPropuestaService,
    private objetivosService: TiposObejtivosService, private activatedRoute: ActivatedRoute, private ideaService: IdeasProyectosService, private ideaComponent: GestionideasComponent,
    private organizacionService: OrganizacionStrategosService, public documentoService: IdeasDocumentosService) { }

  ngOnInit(): void {
    this.isAdmin = this.ideaComponent.isAdmin;
    this.estatusService.getEstatusList().subscribe(response =>{this.estatus = response}); // obtiene los estatus
    this.propuestasService.getTiposPropuestaList().subscribe(response =>{this.propuestas = response}); // obtiene las propuestas
    this.objetivosService.getTiposObjetivoList().subscribe(response =>{this.objetivos = response}); // obtiene los objetivos
    this.organizacionService.getOrganizacionesList().subscribe(response =>{this.organizaciones = response}); // obtiene los objetivos

    if(this.idea != null){
      if(this.idea.documentoId != undefined){
        this.documentoService.getDocumento(this.idea.documentoId).subscribe(response => this.documento = response);
      }
    }
    

  }

  create(): void{
    if(this.isAdmin == true){
      this.idea.dependenciaId = this.ideaComponent.organizacionId;
    }
    console.log(this.idea);
    this.ideaService.create(this.idea).subscribe(
      json => {
      swal.fire('Nueva Idea', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.ideaComponent.getIdeas();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.ideaService.update(this.idea).subscribe(json =>{
      swal.fire('Idea Actualizada',  `${json.mensaje}`, 'success')
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
    this.documentoService.subirDocumento(this.idea.ideaId, this.documento, this.archivoSeleccionado).subscribe(
      
      json => {
        swal.fire('Documento subido', `${json.mensaje}`, 'success');
        this.documento= json.documento;
        this.idea.documentoId= this.documento.documentoId;
        console.log(this.idea);
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
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.value) {
        this.documentoService.deleteDocumento(this.idea.documentoId).subscribe(
          response =>{
            swal.fire(
              'Documento eliminado!',
              'El documento se ha eliminado con éxito',
              'success'
            )
          }
        )
        this.idea.documentoId = 0;
      }
    })
    
  }

  descargarDocumento(){
    window.open(`${this.urlEndPoint}download/${this.idea.documentoId}`); 
  }

}
