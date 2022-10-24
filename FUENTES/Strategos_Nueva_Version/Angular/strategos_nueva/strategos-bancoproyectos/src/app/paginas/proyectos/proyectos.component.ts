import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ORGANIZACION_ID, URL_BACKEND } from 'src/app/config/config';
import Swal from 'sweetalert2';
import { IniciativaEstatusStrategos } from '../configuracion/model-util/IniciativaEstatusStrategos';
import { OrganizacionStrategos } from '../configuracion/model-util/organizacionstrategos';
import { TipoProyectoStrategos } from '../configuracion/model-util/TipoProyectoStrategos';
import { Proyectos } from '../configuracion/model/proyectos';
import { IniciativaEstatusStrategosService } from '../configuracion/services-util/iniciativaestatusstrategos.service';
import { OrganizacionStrategosService } from '../configuracion/services-util/organizacionstrategos.service';
import { TipoProyectoStrategosService } from '../configuracion/services-util/tipoproyectostrategos.service';
import { ProyectoService } from '../configuracion/services/proyectos.service';
import { ModalService } from '../gestionideas/detallegestion/modal.service';


@Component({
  selector: 'app-proyectos',
  templateUrl: './proyectos.component.html',
  styleUrls: ['./proyectos.component.css']
})
export class ProyectosComponent implements OnInit {

  pagePrepro: number =1;
 


  public proyectos: Proyectos[];
  paginador: any;
  proyectoSeleccionado: Proyectos;
  public organizacion: OrganizacionStrategos = new OrganizacionStrategos;
  public organizacionId: any;
  public organizacionFiltroId: any;
  public tipoId: any;
  public estatusId: any;

  public tipos: TipoProyectoStrategos[];
  public estatus: IniciativaEstatusStrategos[];

  public organizaciones: OrganizacionStrategos[];
  public anio: string;
  public historico: number;

  public isAdmin: boolean = false;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;
  urlTree: any;

  constructor(private router: Router, private organizacionesService: OrganizacionStrategosService, private tiposService: TipoProyectoStrategosService, private estatusService: IniciativaEstatusStrategosService,
    private proyectoService: ProyectoService, private modalService: ModalService) {
    this.urlTree = this.router.parseUrl(this.router.url);
    this.organizacionId = this.urlTree.queryParams['id']; 
  }

  ngOnInit(): void {
    
    localStorage.removeItem('objetivoId');
 
    if(this.organizacionId == undefined){
      this.organizacionId = localStorage.getItem('organizacion');
    }
    this.tiposService.getTipoProyectosList().subscribe(response =>{this.tipos = response});
    this.estatusService.getIniciativaEstatusList().subscribe(response =>{this.estatus = response});
    this.organizacionesService.getOrganizacionesList().subscribe(response =>{this.organizaciones = response}); // obtiene las organizaciones

    this.organizacionesService.getOrganizacion(this.organizacionId).subscribe((org) =>{ 
      this.organizacion= org;
    });

    if(this.organizacionId == ORGANIZACION_ID){
      this.isAdmin = true;      
    }

    this.getProyectos();

  }

  crearProyecto(){
       
    this.proyectoSeleccionado = new Proyectos();   
    this.proyectoSeleccionado.estatusId = 7;
    this.proyectoSeleccionado.fechaEstatus = new Date(); 
    this.modalService.abrirModal();
  }

  

  limpiar(){
    this.tipoId = 0;
    this.estatusId = 0;
    this.anio = "0";
    this.historico = 0;
    this.organizacionFiltroId = 0;
  }

  buscar(){

    console.log(this.organizacionFiltroId, this.tipoId, this.estatusId, this.anio, this.historico);

    this.proyectos = null;
    this.elements = [];
    this.previous = [];
   
    if(this.isAdmin == true){
      this.proyectoService.getProyectosFiltro(this.organizacionFiltroId, this.tipoId, this.estatusId, this.anio, this.historico).subscribe(response => {
        this.proyectos = response;        
      });
    }else{
      this.proyectoService.getProyectosFiltro(this.organizacionId, this.tipoId, this.estatusId, this.anio, this.historico).subscribe(response => {
        this.proyectos = response;        
      });
    }
  }

  getProyectos(){
    this.proyectos = null;
    this.elements = [];
    this.previous = [];

    if(this.isAdmin == true){
      this.proyectoService.getProyectosListTipoId(false).subscribe(response =>{
        this.proyectos = response; 
      });
    }else{
      this.proyectoService.getProyectosListOrgTipoId(this.organizacionId, false).subscribe(response =>{
        this.proyectos = response;
      });  
    }

  }

  abrirModal(proyecto: Proyectos){
    this.proyectoSeleccionado= proyecto;
    if(this.proyectoSeleccionado != undefined && this.proyectoSeleccionado.proyectoId != null){
      this.proyectoService.getPoblacionesListId(this.proyectoSeleccionado.proyectoId).subscribe(response =>{this.proyectoSeleccionado.poblaciones = response});
      console.log(this.proyectoSeleccionado);
    }
    this.modalService.abrirModal();
  }

 
  descargarPDF(preproyecto: Proyectos){
    window.open(URL_BACKEND+"/api/strategos/bancoproyectos/preproyecto/pdf/" + preproyecto.proyectoId); 
  }

  descargarPDFResumido(){
    if(this.isAdmin == true){
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/preproyecto/pdf/resumido/"+ 0);
    }else{
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/preproyecto/pdf/resumido/"+this.organizacionId);
    }
  }

  descargarXls(preproyecto: Proyectos){
    window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/excel/" + preproyecto.proyectoId); 
  }

  descargarXlsResumido(){
    if(this.isAdmin == true){
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/preproyecto/excel/"+ 0);
    }else{
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/preproyecto/excel/"+this.organizacionId);
    }
  }

  delete(proyecto: Proyectos): void{

    Swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el preproyecto ${proyecto.nombreProyecto} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.proyectoService.delete(proyecto.proyectoId).subscribe(
          response =>{
            this.getProyectos();
            Swal.fire(
              'Preproyecto eliminado!',
              'El Preproyecto ha sido eliminado con éxito',
              'success'
            )
          }
        )
      }
    })

  }

  planificacionSeguimiento(proyecto: Proyectos){

  }

}
