import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ORGANIZACION_ID, URL_BACKEND } from 'src/app/config/config';
import { IniciativaEstatusStrategos } from '../configuracion/model-util/IniciativaEstatusStrategos';
import { OrganizacionStrategos } from '../configuracion/model-util/organizacionstrategos';
import { TipoProyectoStrategos } from '../configuracion/model-util/TipoProyectoStrategos';
import { Proyectos } from '../configuracion/model/proyectos';
import { IniciativaEstatusStrategosService } from '../configuracion/services-util/iniciativaestatusstrategos.service';
import { OrganizacionStrategosService } from '../configuracion/services-util/organizacionstrategos.service';
import { TipoProyectoStrategosService } from '../configuracion/services-util/tipoproyectostrategos.service';
import { ProyectoService } from '../configuracion/services/proyectos.service';
import { ModalService } from './detallepreproyecto/modal.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-preproyecto',
  templateUrl: './preproyecto.component.html',
  styleUrls: ['./preproyecto.component.css']
})
export class PreproyectoComponent implements OnInit {

  pagePrepro: number =1;
 


  public preproyectos: Proyectos[];
  paginador: any;
  preproyectoSeleccionado: Proyectos;
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

    this.getPreproyectos();

  }

  crearPreProyecto(){
       
    this.preproyectoSeleccionado = new Proyectos();   
    this.preproyectoSeleccionado.estatusId = 6;
    this.preproyectoSeleccionado.fechaEstatus = new Date(); 
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

    this.preproyectos = null;
    this.elements = [];
    this.previous = [];
   
    if(this.isAdmin == true){
      this.proyectoService.getProyectosFiltro(this.organizacionFiltroId, this.tipoId, this.estatusId, this.anio, this.historico).subscribe(response => {
        this.preproyectos = response;        
      });
    }else{
      this.proyectoService.getProyectosFiltro(this.organizacionId, this.tipoId, this.estatusId, this.anio, this.historico).subscribe(response => {
        this.preproyectos = response;        
      });
    }
  }

  getPreproyectos(){
    this.preproyectos = null;
    this.elements = [];
    this.previous = [];

    if(this.isAdmin == true){
      this.proyectoService.getProyectosListTipoId(true).subscribe(response =>{
        this.preproyectos = response; 
      });
    }else{
      this.proyectoService.getProyectosListOrgTipoId(this.organizacionId, true).subscribe(response =>{
        this.preproyectos = response;
      });  
    }

  }

  abrirModal(preproyecto: Proyectos){
    this.preproyectoSeleccionado= preproyecto;
    if(this.preproyectoSeleccionado != undefined && this.preproyectoSeleccionado.proyectoId != null){
      this.proyectoService.getPoblacionesListId(this.preproyectoSeleccionado.proyectoId).subscribe(response =>{this.preproyectoSeleccionado.poblaciones = response});
      console.log(this.preproyectoSeleccionado);
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

  delete(preproyecto: Proyectos): void{

    Swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el preproyecto ${preproyecto.nombreProyecto} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.proyectoService.delete(preproyecto.proyectoId).subscribe(
          response =>{
            this.getPreproyectos();
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

}
