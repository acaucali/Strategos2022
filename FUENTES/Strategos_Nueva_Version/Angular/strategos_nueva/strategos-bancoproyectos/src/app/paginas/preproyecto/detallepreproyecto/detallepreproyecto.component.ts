import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IniciativaEstatusStrategos } from '../../configuracion/model-util/IniciativaEstatusStrategos';
import { MetodologiaStrategos } from '../../configuracion/model-util/MetodologiaStrategos';
import { OrganizacionStrategos } from '../../configuracion/model-util/organizacionstrategos';
import { TipoProyectoStrategos } from '../../configuracion/model-util/TipoProyectoStrategos';
import { IdeasProyectos } from '../../configuracion/model/ideasproyectos';
import { Proyectos } from '../../configuracion/model/proyectos';
import { TipoPoblacion } from '../../configuracion/model/tipopoblacion';
import { TiposObjetivos } from '../../configuracion/model/tiposobjetivos';
import { IniciativaEstatusStrategosService } from '../../configuracion/services-util/iniciativaestatusstrategos.service';
import { MetodologiaStrategosService } from '../../configuracion/services-util/metodologiastrategos.service';
import { OrganizacionStrategosService } from '../../configuracion/services-util/organizacionstrategos.service';
import { TipoProyectoStrategosService } from '../../configuracion/services-util/tipoproyectostrategos.service';
import { IdeasProyectosService } from '../../configuracion/services/ideasproyectos.service';
import { TipoPoblacionService } from '../../configuracion/services/tipopoblacion.service';
import { TiposObejtivosService } from '../../configuracion/services/tiposobjetivos.service';
import { GestionideasComponent } from '../../gestionideas/gestionideas.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'detalle-preproyecto',
  templateUrl: './detallepreproyecto.component.html',
  styleUrls: ['./detallepreproyecto.component.css']
})
export class DetallepreproyectoComponent implements OnInit {

  titulo: string = "Datos del Preproyecto - Preproyectos";
  private errores: string[];

  organizaciones: OrganizacionStrategos[];
  metodologias: MetodologiaStrategos[];
  alineaciones: TiposObjetivos[];
  estatus: IniciativaEstatusStrategos[];
  tipos: TipoProyectoStrategos[];
  ideas: IdeasProyectos[];
  poblaciones: TipoPoblacion[];
  idea: IdeasProyectos;   
  
  @Input() preproyecto: Proyectos = new Proyectos;  

  constructor(public modalService: ModalService, private router: Router, private organizacionService: OrganizacionStrategosService, private ideaService: IdeasProyectosService,
    private tipoService: TipoProyectoStrategosService, private alineacionService: TiposObejtivosService, private estatusService: IniciativaEstatusStrategosService,
    private metodologiaService: MetodologiaStrategosService, private poblacionService: TipoPoblacionService) { }

  ngOnInit(): void {

    this.estatusService.getIniciativaEstatusList().subscribe(response =>{this.estatus = response}); // obtiene los estatus
    this.organizacionService.getOrganizacionesList().subscribe(response =>{this.organizaciones = response});
    this.alineacionService.getTiposObjetivoList().subscribe(response =>{this.alineaciones = response});
    this.metodologiaService.getMetodologiasList().subscribe(response =>{this.metodologias = response});
    this.tipoService.getTipoProyectosList().subscribe(response =>{this.tipos = response});
    this.ideaService.getIdeasList().subscribe(response =>{this.ideas = response});
    this.poblacionService.getPoblacionesList().subscribe(response =>{this.poblaciones = response});

  }

  cerrarModal(){
    this.modalService.cerrarModal();
  }

}
  