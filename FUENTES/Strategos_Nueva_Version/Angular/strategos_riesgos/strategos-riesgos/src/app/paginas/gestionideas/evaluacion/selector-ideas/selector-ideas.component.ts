import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ORGANIZACION_ID } from 'src/app/config/config';
import { OrganizacionStrategos } from 'src/app/paginas/configuracion/model-util/organizacionstrategos';
import { EstatusIdeas } from 'src/app/paginas/configuracion/model/estatusideas';
import { IdeasProyectos } from 'src/app/paginas/configuracion/model/ideasproyectos';
import { TiposObjetivos } from 'src/app/paginas/configuracion/model/tiposobjetivos';
import { TiposPropuestas } from 'src/app/paginas/configuracion/model/tipospropuestas';
import { OrganizacionStrategosService } from 'src/app/paginas/configuracion/services-util/organizacionstrategos.service';
import { EstatusIdeaService } from 'src/app/paginas/configuracion/services/estatusideas.service';
import { EvaluacionIdeasService } from 'src/app/paginas/configuracion/services/evaluacionidea.service';
import { IdeasProyectosService } from 'src/app/paginas/configuracion/services/ideasproyectos.service';
import { TiposObejtivosService } from 'src/app/paginas/configuracion/services/tiposobjetivos.service';
import { TiposPropuestaService } from 'src/app/paginas/configuracion/services/tipospropuestas.service';
import { EvaluacionComponent } from '../evaluacion.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'selector-ideas',
  templateUrl: './selector-ideas.component.html',
  styleUrls: ['./selector-ideas.component.css']
})
export class SelectorIdeasComponent implements OnInit {

  public ideas: IdeasProyectos[];
  public ideasEvaluacion: IdeasProyectos[] = new Array<IdeasProyectos>(); 
  
  private errores: string[];
  @Input() org: number;

  elements: any = [];
  previous: any = [];

  pageSele: number =1;
  paginador: any;
  public anio: string;
  public organizacionFiltroId: any;
  public organizacionId: any;
  public propuestaId: any;
  public estatusId: any;
  public historico: number;
  public isAdmin: boolean = false;
  public propuestas: TiposPropuestas[];
  public objetivos: TiposObjetivos[];
  public estatus: EstatusIdeas[];
  public organizaciones: OrganizacionStrategos[];

  constructor(public modalservice: ModalService, private router: Router, private evaluacionService: EvaluacionIdeasService, private evaluacionComponent: EvaluacionComponent
    , private activatedRoute: ActivatedRoute, private ideasService: IdeasProyectosService, private organizacionesService: OrganizacionStrategosService, private estatusService: EstatusIdeaService, private propuestasService: TiposPropuestaService,
    private objetivosService: TiposObejtivosService) { }

  ngOnInit(): void {

               
      if(this.org == ORGANIZACION_ID){
        this.ideasService.getIdeasList().subscribe(response => {this.ideas = response}); 
        this.isAdmin = true;
      }else{
        this.ideasService.getIdeasListId(this.org).subscribe(response =>{this.ideas = response});
      }
       
      this.estatusService.getEstatusList().subscribe(response =>{this.estatus = response}); // obtiene los estatus
      this.propuestasService.getTiposPropuestaList().subscribe(response =>{this.propuestas = response}); // obtiene las propuestas
      this.objetivosService.getTiposObjetivoList().subscribe(response =>{this.objetivos = response}); // obtiene los objetivos
      this.organizacionesService.getOrganizacionesList().subscribe(response =>{this.organizaciones = response}); // obtiene las organizaciones
  }
   
  cerrarModal(){
    this.modalservice.cerrarModal();
  }

  seleccionarIdea(idea: IdeasProyectos){
    this.ideasEvaluacion.push(idea);
    this.evaluacionComponent.ideas = null;
    this.evaluacionComponent.ideas = this.ideasEvaluacion;
  }

  limpiar(){
    this.propuestaId = 0;
    this.estatusId = 0;
    this.anio = "0";
    this.historico = 0;
    this.organizacionFiltroId = 0;
  }

  buscar(){
    this.ideas = null;
     
    if(this.isAdmin == true){
      this.ideasService.getIdeasFiltro(this.organizacionFiltroId, this.propuestaId, this.estatusId, this.anio, this.historico).subscribe(response => {
        this.ideas = response;
        
      });
    }else{
      this.ideasService.getIdeasFiltro(this.organizacionId, this.propuestaId, this.estatusId, this.anio, this.historico).subscribe(response => {
        this.ideas = response;
        
      });
    }
    
  }

}
