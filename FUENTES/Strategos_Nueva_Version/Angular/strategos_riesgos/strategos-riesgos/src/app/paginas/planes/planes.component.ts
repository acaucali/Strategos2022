import { Component, OnInit, ViewChild } from '@angular/core';
import { OrganizacionStrategos } from '../configuracion/model-util/organizacionstrategos';
import { OrganizacionStrategosService } from '../configuracion/services-util/organizacionstrategos.service';
import { ProyectoService } from '../configuracion/services/proyectos.service';
import { Proyectos } from '../configuracion/model/proyectos';
import { ActivatedRoute, Router } from '@angular/router';
import { Plan } from '../configuracion/model/plan';
import { PlanService } from '../configuracion/services/plan.service';
import { ProyectosPlan } from '../configuracion/model/proyectosplan';
import { PlanComponent } from './plan/plan.component';
import { IndicadoresComponent } from './indicadores/indicadores.component';
import { IniciativasComponent } from './iniciativas/iniciativas.component';

@Component({
  selector: 'app-planes',
  templateUrl: './planes.component.html',
  styleUrls: ['./planes.component.css']
})
export class PlanesComponent implements OnInit {

  public organizacionId: any;
  public organizacion: OrganizacionStrategos = new OrganizacionStrategos;
  public proyecto: Proyectos = new Proyectos;
  public plan: Plan = new Plan;
  public planId: any;
  public proyectoPlan: ProyectosPlan = new ProyectosPlan;
  planSeleccionado: Plan;

  @ViewChild(IniciativasComponent) iniciativaComponent: IniciativasComponent;
  @ViewChild(IndicadoresComponent) indicadorComponent: IndicadoresComponent;
  @ViewChild(PlanComponent) planComponent: PlanComponent;

  constructor(private organizacionesService: OrganizacionStrategosService, private proyectoService: ProyectoService, private activatedRoute: ActivatedRoute,
    private planService: PlanService, private router: Router) { }

  ngOnInit(): void {

    localStorage.removeItem('anio');
    localStorage.removeItem('periodoFin');
    localStorage.removeItem('periodoIni');
    localStorage.removeItem('frecuencia');
    localStorage.removeItem('real');
    localStorage.removeItem('meta');
    localStorage.removeItem('tarea');
    localStorage.removeItem('actividad');
    localStorage.removeItem('actividadNombre');
    localStorage.removeItem('tareaNombre');
    localStorage.removeItem("indicador");
    localStorage.removeItem("periodos");
    localStorage.removeItem("reales");
    localStorage.removeItem("metas");
    

    this.organizacionId = localStorage.getItem('organizacion');

    this.organizacionesService.getOrganizacion(this.organizacionId).subscribe((org) =>{ 
      this.organizacion= org;
    });

    this.activatedRoute.params.subscribe(params =>{
      let proyectoId = params['id'];
      localStorage.setItem('proyectoId', proyectoId);

      this.proyectoService.getProyecto(proyectoId).subscribe((pro) =>{ 
        this.proyecto= pro;
      });

      this.planService.getProyectoPlanListId(proyectoId).subscribe((pro) =>{ 
        this.proyectoPlan= pro;   
        this.planService.getPlan(pro.planId).subscribe((pla) =>{ 
          this.plan= pla;
          localStorage.setItem('planId', pla.planId.toString());
          this.planComponent.getPerspectivas();
        });
        this.planId = this.plan.planId;        
      });

    });

    console.log(this.proyectoPlan);

  }

  getIndicadoresIniciativas(){
    this.indicadorComponent.getIndicadores();
    this.iniciativaComponent.getIniciativas();

  }

  regresar(){
    this.router.navigate(['/', 'proyecto']);
  }

}
