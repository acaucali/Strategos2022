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
    this.modalService.abrirModal();
  }

  descargarPDFResumido(){
    if(this.isAdmin == true){
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/pdf/resumido/"+ 0);
    }else{
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/pdf/resumido/"+this.organizacionId);
    }
  }

  
  descargarXlsResumido(){
    if(this.isAdmin == true){
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/excel/"+ 0);
    }else{
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/excel/"+this.organizacionId);
    }
  }  

  limpiar(){
    this.tipoId = 0;
    this.estatusId = 0;
    this.anio = "0";
    this.historico = 0;
    this.organizacionFiltroId = 0;
  }

  buscar(){
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
      this.proyectoService.getProyectosList().subscribe(response =>{
        this.preproyectos = response; 
      });
    }else{
      this.proyectoService.getProyectosListId(this.organizacionId).subscribe(response =>{
        this.preproyectos = response;
      });  
    }

  }

  abrirModal(preproyecto: Proyectos){
    this.preproyectoSeleccionado= preproyecto;
    this.modalService.abrirModal();
  }

  crearIdea(){
    this.preproyectoSeleccionado = new Proyectos();
    
    this.modalService.abrirModal();
  }

}
