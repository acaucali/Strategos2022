package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.sl.usermodel.TextParagraph.FontAlign;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.strategos.nueva.bancoproyecto.ideas.model.Actividad;
import com.strategos.nueva.bancoproyecto.ideas.model.EstatusIdeas;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeas;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasEvaluadas;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.Iniciativa;
import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPerspectiva;
import com.strategos.nueva.bancoproyecto.ideas.model.Presupuesto;
import com.strategos.nueva.bancoproyecto.ideas.model.PresupuestoDatos;
import com.strategos.nueva.bancoproyecto.ideas.model.TiposObjetivos;
import com.strategos.nueva.bancoproyecto.ideas.model.TiposPropuestas;
import com.strategos.nueva.bancoproyecto.ideas.service.ActividadService;
import com.strategos.nueva.bancoproyecto.ideas.service.EstatusIdeaService;
import com.strategos.nueva.bancoproyecto.ideas.service.EvaluacionIdeasDetalleService;
import com.strategos.nueva.bancoproyecto.ideas.service.EvaluacionIdeasService;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasEvaluadasService;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasProyectosService;
import com.strategos.nueva.bancoproyecto.ideas.service.IniciativaPerspectivaService;
import com.strategos.nueva.bancoproyecto.ideas.service.IniciativaService;
import com.strategos.nueva.bancoproyecto.ideas.service.PresupuestoDatosService;
import com.strategos.nueva.bancoproyecto.ideas.service.PresupuestoService;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosPoblacionService;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosService;
import com.strategos.nueva.bancoproyecto.ideas.service.TipoPoblacionService;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposObjetivosService;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectiva;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.IniciativaEstatusStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.MetodologiaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.PerspectivaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.TipoProyectoStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorPerspectivaService;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorService;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorTareaService;
import com.strategos.nueva.bancoproyecto.strategos.service.IniciativaEstatusService;
import com.strategos.nueva.bancoproyecto.strategos.service.MetodologiaService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;
import com.strategos.nueva.bancoproyecto.strategos.service.PerspectivaService;
import com.strategos.nueva.bancoproyecto.strategos.service.TipoProyectoService;
import com.strategos.nueva.bancoproyectos.model.util.DatoIdea;
import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosPoblacion;
import com.strategos.nueva.bancoproyecto.ideas.model.TipoPoblacion;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class ReportesRestController {

	@Autowired
	private IdeasProyectosService ideasProyectosService;
	@Autowired
	private EstatusIdeaService estatusService;
	@Autowired
	private TiposObjetivosService objetivosService;
	@Autowired
	private TiposPropuestasService propuestasService;
	@Autowired
	private OrganizacionService organizacionService;
	@Autowired
	private EvaluacionIdeasDetalleService evaluacionDetalleService;
	@Autowired
	private IdeasEvaluadasService ideasEvaluadasService;
	@Autowired
	private EvaluacionIdeasService evaluacionService;
	@Autowired
	private ProyectosService preproyectoService;
	@Autowired
	private TipoProyectoService tipoProyectoService;
	@Autowired
	private IniciativaEstatusService iniciativaEstatusService;
	@Autowired
	private MetodologiaService metodologiasService;
	@Autowired
	private TipoPoblacionService tipoPoblacionService;
	@Autowired
	private ProyectosPoblacionService proyectoPoblacionService;
	@Autowired
	private ActividadService actividadService;
	@Autowired
	private IndicadorTareaService indicadorTareaService;
	@Autowired
	private PresupuestoService presupuestoService;
	@Autowired
	private PresupuestoDatosService presupuestoDatosService;
	@Autowired
	private PerspectivaService perspectivaService;
	@Autowired
	private IndicadorPerspectivaService indicadorPerspectivaService;
	@Autowired
	private IndicadorService indicadorService;
	@Autowired
	private IniciativaPerspectivaService iniciativaPerspectivaService;
	@Autowired
	private IniciativaService iniciativaService;

	// Servicios Rest tabla - idea
	private final Logger log = LoggerFactory.getLogger(IdeasProyectosRestController.class);

	@GetMapping("/idea/excel/{orgId}") // ideas detalle
	public @ResponseBody ResponseEntity<InputStreamResource> exportToXls(@PathVariable Long orgId) throws IOException {

		List<IdeasProyectos> ideasOrg = ideasProyectosService.findAll();
		List<IdeasProyectos> ideasFin = new ArrayList();
		ByteArrayInputStream bis;

		if (orgId == 0) {
			bis = createReportXls(ideasOrg);
		} else {

			ideasFin = ideasProyectosService.findAllByDependenciaId(orgId);

			bis = createReportXls(ideasFin);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=ideasDetalle.xls");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bis));
	}

	@GetMapping("/idea/evaluaciondetalle/excel/{evaluacionId}") // ideas detalle
	public @ResponseBody ResponseEntity<InputStreamResource> exportXlsDetalle(@PathVariable Long evaluacionId)
			throws IOException {

		List<EvaluacionIdeasDetalle> evaluacionesDetalle = evaluacionDetalleService.findAllByEvaluacionId(evaluacionId);
		List<IdeasEvaluadas> ideasEvaluadas = new ArrayList<IdeasEvaluadas>();
		ideasEvaluadas = ideasEvaluadasService.findAllByEvaluacionId(evaluacionId);
		ByteArrayInputStream bis;

		bis = createReportEvaluacionXls(ideasEvaluadas, evaluacionesDetalle, evaluacionId);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=evalucacionIdeasDetalle.xls");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bis));
	}

	@GetMapping("/idea/pdf/{ideaId}") // ideas detalle
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDF(@PathVariable Long ideaId) throws IOException {

		IdeasProyectos idea = ideasProyectosService.findById(ideaId);

		ByteArrayInputStream bis = createReportDetalle(idea);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=ideasDetalle.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@GetMapping("/idea/pdf/resumido/{orgId}")
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDFResumido(@PathVariable Long orgId)
			throws IOException {

		List<IdeasProyectos> ideasOrg = ideasProyectosService.findAll();
		List<IdeasProyectos> ideasFin = new ArrayList();
		ByteArrayInputStream bis;

		if (orgId == 0) { // todas las organizaciones
			bis = createReport(ideasOrg);
		} else {
			ideasFin = ideasProyectosService.findAllByDependenciaId(orgId);
			bis = createReport(ideasFin);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=ideas.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}
	// reportes preproyectos

	@GetMapping("/preproyecto/excel/{orgId}") // preproyecto detalle
	public @ResponseBody ResponseEntity<InputStreamResource> exportToXlsPreProyectos(@PathVariable Long orgId)
			throws IOException {

		List<Proyectos> proyectosOrg = preproyectoService.findAll();
		List<Proyectos> preproyectosFin = new ArrayList();
		ByteArrayInputStream bis;

		if (orgId == 0) {
			bis = createReportXlsPreproyectos(proyectosOrg);
		} else {

			preproyectosFin = preproyectoService.findAllByDependenciaId(orgId);

			bis = createReportXlsPreproyectos(preproyectosFin);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=proyectosDetalle.xls");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bis));
	}

	@GetMapping("/preproyecto/pdf/{proyectoId}") // preproyecto detalle
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDFPreproyectos(@PathVariable Long proyectoId)
			throws IOException {

		Proyectos pre = preproyectoService.findById(proyectoId);

		ByteArrayInputStream bis = createReportDetallePreproyecto(pre);

		HttpHeaders headers = new HttpHeaders();
		if (pre.getIsPreproyecto())
			headers.add("Content-Disposition", "inline; filename=preproyectosDetalle.pdf");
		else
			headers.add("Content-Disposition", "inline; filename=proyectoDetalle.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@GetMapping("/preproyecto/pdf/resumido/{orgId}")
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDFPreproyectosResumido(@PathVariable Long orgId)
			throws IOException {

		List<Proyectos> proyectosOrg = preproyectoService.findAll();
		List<Proyectos> preproyectosFin = new ArrayList();
		ByteArrayInputStream bis;

		if (orgId == 0) { // todas las organizaciones
			bis = createReportPreproyecto(proyectosOrg);
		} else {
			preproyectosFin = preproyectoService.findAllByDependenciaId(orgId);
			bis = createReportPreproyecto(preproyectosFin);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=proyectos.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@GetMapping("/presupuesto/excel/{actId}") // preproyecto detalle
	public @ResponseBody ResponseEntity<InputStreamResource> exportToXlsPresupuesto(@PathVariable Long actId)
			throws IOException {

		List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
		List<Actividad> actividades = actividadService.findAllByProyectoId(actId);

		ByteArrayInputStream bis;

		bis = createReportXlsPresupuestos(actividades);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=presupuesto.xls");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bis));
	}

	// reportes plan

	@GetMapping("/plan/pdf/{planId}")
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPdfPlan(@PathVariable Long planId)
			throws IOException {
		List<PerspectivaStrategos> perspectivas = perspectivaService.findAllByPlanId(planId);
		ByteArrayInputStream bis;

		bis = createReportePlanPdf(perspectivas);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=planResumido.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@GetMapping("/plan/excel/{planId}")
	public @ResponseBody ResponseEntity<InputStreamResource> exportToXlsPlan(@PathVariable Long planId)
			throws IOException {
		List<PerspectivaStrategos> perspectivas = perspectivaService.findAllByPlanId(planId);
		ByteArrayInputStream bis;

		bis = createReportePlanXls(perspectivas);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=planResumido.xls");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bis));
	}

	// Funciones Reportes

	public ByteArrayInputStream createReport(List<IdeasProyectos> ideas) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfWriter.getInstance(document, out);
			document.open();

			document.addTitle("Reporte ideas resumido");
			Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
			Paragraph p = new Paragraph("Listado resumido de Ideas de Pre-Proyecto", font);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(5);

			addTableHeader(table);

			for (IdeasProyectos ide : ideas) {
				addRows(table, ide);
			}

			document.add(table);
			document.close();

		} catch (DocumentException ex) {

			System.out.println("error");
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream createReportEvaluacionXls(List<IdeasEvaluadas> ideasEvaluadas,
			List<EvaluacionIdeasDetalle> evaluacionesDetalle, Long evaluacionId) throws IOException {

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet("Evaluación Ideas Detalle");

			org.apache.poi.ss.usermodel.Font headerFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
			((org.apache.poi.ss.usermodel.Font) headerFont).setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// Header Row
			org.apache.poi.ss.usermodel.Font titleFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
			((org.apache.poi.ss.usermodel.Font) titleFont).setBold(true);

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setFont(titleFont);

			Row titleRow = sheet.createRow(0);
			Cell celdaTitle = titleRow.createCell(0);
			celdaTitle.setCellValue("Listado Detallado de Evaluaciones");
			celdaTitle.setCellStyle(titleCellStyle);

			EvaluacionIdeas evaluacion = evaluacionService.findById(evaluacionId);

			Row titleRow2 = sheet.createRow(2);
			Cell celdaTitle2 = titleRow2.createCell(0);
			celdaTitle2.setCellValue("Fecha Evaluación");
			celdaTitle2.setCellStyle(titleCellStyle);

			SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
			Cell celdaTitle21 = titleRow2.createCell(1);
			celdaTitle21.setCellValue(formateadorFecha.format(evaluacion.getFechaEvaluacion()));

			Row titleRow3 = sheet.createRow(3);
			Cell celdaTitle3 = titleRow3.createCell(0);
			celdaTitle3.setCellValue("Descripción de evaluación");
			celdaTitle3.setCellStyle(titleCellStyle);

			Cell celdaTitle31 = titleRow3.createCell(1);
			celdaTitle31.setCellValue(evaluacion.getObservacion());

			Row headerRow = sheet.createRow(4);

			int col = 0;
			Cell cell;

			cell = headerRow.createCell(col);
			cell.setCellValue("Nombre Idea");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Dependencia");
			cell.setCellStyle(headerCellStyle);
			col++;

			// ciclo que busca los encabezados
			for (IdeasEvaluadas ide : ideasEvaluadas) {

				IdeasProyectos idea = ideasProyectosService.findById(ide.getIdeaId());

				for (EvaluacionIdeasDetalle eva : evaluacionesDetalle) {
					if (eva.getIdeaId().equals(ide.getIdeaId())) {

						cell = headerRow.createCell(col);
						cell.setCellValue(eva.getCriterio() + " (" + eva.getPeso() + " %)");
						cell.setCellStyle(headerCellStyle);
						col++;
					}
				}

				break;
			}

			cell = headerRow.createCell(col);
			cell.setCellValue("Puntaje Total");
			cell.setCellStyle(headerCellStyle);
			col++;

			int row = 6;

			List<Long> ids = new ArrayList<Long>();
			List<String> ideas = ordenarIdeas(ideasEvaluadas);

			for (String idea : ideas) {
				String carId = idea.substring(idea.indexOf("-") + 1, idea.length());
				ids.add(new Long(carId));
			}

			for (Long id : ids) {

				int colu = 0;

				Row headerRow2 = sheet.createRow(row);

				IdeasProyectos idea = ideasProyectosService.findById(id);

				Cell celda = headerRow2.createCell(colu);
				celda.setCellValue(idea.getNombreIdea());
				colu++;

				celda = headerRow2.createCell(colu);
				celda.setCellValue(idea.getOrganizacion());
				colu++;

				for (EvaluacionIdeasDetalle eva : evaluacionesDetalle) {
					if (eva.getIdeaId().equals(id)) {
						cell = headerRow2.createCell(colu);
						cell.setCellValue(eva.getValorEvaluado());
						colu++;
					}
				}

				celda = headerRow2.createCell(colu);
				celda.setCellValue(idea.getValorUltimaEvaluacion());
				colu++;

				row++;

			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public ByteArrayInputStream createReportXlsPresupuestos(List<Actividad> actividades) throws IOException {

		List<Presupuesto> presupuestos = presupuestoService.findAll();

		int columns = 0;

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet("Presupuestos");

			org.apache.poi.ss.usermodel.Font headerFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
			((org.apache.poi.ss.usermodel.Font) headerFont).setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// Header Row
			org.apache.poi.ss.usermodel.Font titleFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
			((org.apache.poi.ss.usermodel.Font) titleFont).setBold(true);

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setFont(titleFont);

			Row titleRow = sheet.createRow(0);
			Cell celdaTitle = titleRow.createCell(0);
			celdaTitle.setCellValue("Listado de Presupuestos");
			celdaTitle.setCellStyle(titleCellStyle);

			Row headerRow = sheet.createRow(2);

			int col = 0;
			Cell cell;

			cell = headerRow.createCell(col);
			cell.setCellValue("Real");
			cell.setCellStyle(headerCellStyle);
			col++;

			for (Presupuesto pre : presupuestos) {

				cell = headerRow.createCell(col);
				cell.setCellValue(pre.getPresupuesto());
				cell.setCellStyle(headerCellStyle);
				col++;

			}

			cell = headerRow.createCell(col);
			cell.setCellValue("Total");
			cell.setCellStyle(headerCellStyle);
			col++;

			int row = 3;
			int colu = 0;

			for (Actividad act : actividades) {// real
				int col2 = 1;
				Row headerRow2 = sheet.createRow(row);
				Cell celda = headerRow2.createCell(0);
				celda.setCellValue(act.getNombreActividad());
				List<PresupuestoDatos> preAct = presupuestoDatosService.findAllByTareaId(act.getActividadId());
				Double acumulado = 0.0;
				for (PresupuestoDatos pre : preAct) {
					if (pre.getSerieId() == 0) {
						acumulado = acumulado + pre.getValor();
						celda = headerRow2.createCell(col2);
						celda.setCellValue(pre.getValor());
						col2++;
					}
				}
				celda = headerRow2.createCell(col2);
				celda.setCellValue(acumulado);

				row++;
			}
			Row headerRow2 = sheet.createRow(row);
			Cell celda = headerRow2.createCell(0);
			celda.setCellValue("Total");

			Double valor = 0.0;
			for (Integer x = 1; x < 7; x++) {
				valor = 0.0;
				for (Integer y = 3; y < 6; y++) {
					Row headerRow3 = sheet.getRow(y);
					Cell celdaV = headerRow3.getCell(x);
					celdaV.setCellType(CellType.STRING);
					String valorA = celdaV.getStringCellValue();
					valor = valor + Double.valueOf(valorA);
					celda = headerRow2.createCell(x);
					celda.setCellValue(valor);
				}
			}

			Row headerRow4 = sheet.createRow(9);

			int col3 = 0;
			Cell cell2;

			cell2 = headerRow4.createCell(col3);
			cell2.setCellValue("Meta");
			cell2.setCellStyle(headerCellStyle);
			col3++;

			for (Presupuesto pre : presupuestos) {

				cell2 = headerRow4.createCell(col3);
				cell2.setCellValue(pre.getPresupuesto());
				cell2.setCellStyle(headerCellStyle);
				col3++;

			}

			cell2 = headerRow4.createCell(col3);
			cell2.setCellValue("Total");
			cell2.setCellStyle(headerCellStyle);
			col3++;
			row = 10;

			for (Actividad act : actividades) {// real
				int col2 = 1;
				Row headerRow3 = sheet.createRow(row);
				Cell celda1 = headerRow3.createCell(0);
				celda1.setCellValue(act.getNombreActividad());
				List<PresupuestoDatos> preAct = presupuestoDatosService.findAllByTareaId(act.getActividadId());
				Double acumulado = 0.0;
				for (PresupuestoDatos pre : preAct) {
					if (pre.getSerieId() == 1) {
						acumulado = acumulado + pre.getValor();
						celda1 = headerRow3.createCell(col2);
						celda1.setCellValue(pre.getValor());
						col2++;
					}
				}
				celda1 = headerRow3.createCell(col2);
				celda1.setCellValue(acumulado);

				row++;
			}
			Row headerRow3 = sheet.createRow(row);
			Cell celda1 = headerRow3.createCell(0);
			celda1.setCellValue("Total");

			Double valor1 = 0.0;
			for (Integer x = 1; x < 7; x++) {
				valor1 = 0.0;
				for (Integer y = 10; y < 13; y++) {
					Row headerRow5 = sheet.getRow(y);
					Cell celdaV = headerRow5.getCell(x);
					celdaV.setCellType(CellType.STRING);
					String valorA = celdaV.getStringCellValue();
					valor1 = valor1 + Double.valueOf(valorA);
					celda1 = headerRow3.createCell(x);
					celda1.setCellValue(valor1);
				}
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public ByteArrayInputStream createReportXlsPreproyectos(List<Proyectos> proyectos) throws IOException {

		int columns = 0;

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet("Proyectos");

			org.apache.poi.ss.usermodel.Font headerFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
			((org.apache.poi.ss.usermodel.Font) headerFont).setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// Header Row
			org.apache.poi.ss.usermodel.Font titleFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
			((org.apache.poi.ss.usermodel.Font) titleFont).setBold(true);

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setFont(titleFont);

			Row titleRow = sheet.createRow(0);
			Cell celdaTitle = titleRow.createCell(0);
			celdaTitle.setCellValue("Listado de Proyectos");
			celdaTitle.setCellStyle(titleCellStyle);

			Row headerRow = sheet.createRow(2);

			int col = 0;
			Cell cell;

			cell = headerRow.createCell(col);
			cell.setCellValue("Nombre Proyecto");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Idea Propuesta");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Dependencia Responsable");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Profesional Responsable");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Teléfono Contacto");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Correo electronico");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Fecha Radicación");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Año Formulación");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Tipología Proyecto");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Estatus Proyecto");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Fecha Estatus");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Relación Objetivos");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Código BDP");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Duración (meses)");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Costo Estimado");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Dependencia Lider");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Pertinencia");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Problemática / Necesidad");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Dependencias Estratégicas");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Socios Estratégicos");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Roles Entidades");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Posibles Fuentes Financiación");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Cobertura");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Población Beneficiaria");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Antecedentes");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Justificación");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Alcance del Proyecto");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Objetivo General");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Metodología");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Historico");
			cell.setCellStyle(headerCellStyle);
			col++;

			int row = 3;
			int colu = 0;

			for (Proyectos pro : proyectos) {

				TipoProyectoStrategos tipo = tipoProyectoService.findById(pro.getTipoProyectoId());
				IniciativaEstatusStrategos est = iniciativaEstatusService.findById(pro.getEstatusId());
				TiposObjetivos tip = objetivosService.findById(pro.getTipoObjetivoId());
				MetodologiaStrategos met = metodologiasService.findById(pro.getMetodologiaId());

				List<ProyectosPoblacion> poblaciones = proyectoPoblacionService
						.findAllByProyectoId(pro.getProyectoId());

				OrganizacionesStrategos org = organizacionService.findById(pro.getDependenciaId());
				OrganizacionesStrategos orgLider = organizacionService.findById(pro.getDependenciaLider());

				SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");

				String fecha = formateadorFecha.format(pro.getFechaRadicacion());
				String fechaEstatus = formateadorFecha.format(pro.getFechaEstatus());
				String historico = "";
				if (pro.getHistorico() != null && pro.getHistorico()) {
					historico = "Si";
				} else {
					historico = "No";
				}

				Row headerRow2 = sheet.createRow(row);

				Cell celda = headerRow2.createCell(0);
				celda.setCellValue(pro.getNombreProyecto());

				if (pro.getIdeaId() != null) {
					IdeasProyectos idea = ideasProyectosService.findById(pro.getIdeaId());
					celda = headerRow2.createCell(1);
					celda.setCellValue(idea.getNombreIdea());
				} else {
					celda = headerRow2.createCell(1);
					celda.setCellValue("");
				}

				celda = headerRow2.createCell(2);
				celda.setCellValue(org.getNombre());

				celda = headerRow2.createCell(3);
				celda.setCellValue(pro.getProfesionalResponsable());

				celda = headerRow2.createCell(4);
				celda.setCellValue(pro.getContactoTelefono());

				celda = headerRow2.createCell(5);
				celda.setCellValue(pro.getContactoEmail());

				celda = headerRow2.createCell(6);
				celda.setCellValue(fecha);

				celda = headerRow2.createCell(7);
				celda.setCellValue(pro.getAnioFormulacion());

				celda = headerRow2.createCell(8);
				celda.setCellValue(tipo.getNombre());

				celda = headerRow2.createCell(9);
				celda.setCellValue(est.getNombre());

				celda = headerRow2.createCell(10);
				celda.setCellValue(fechaEstatus);

				celda = headerRow2.createCell(11);
				celda.setCellValue(tip.getDescripcionObjetivo());

				celda = headerRow2.createCell(12);
				celda.setCellValue(pro.getCodigoBdp());

				celda = headerRow2.createCell(13);
				celda.setCellValue(pro.getDuracion().toString());

				celda = headerRow2.createCell(14);
				celda.setCellValue(pro.getCostoEstimado());

				celda = headerRow2.createCell(15);
				celda.setCellValue(orgLider.getNombre());

				celda = headerRow2.createCell(16);
				celda.setCellValue(pro.getPertinencia());

				celda = headerRow2.createCell(17);
				celda.setCellValue(pro.getProblematica());

				celda = headerRow2.createCell(18);
				celda.setCellValue(pro.getDependenciasEstrategicas());

				celda = headerRow2.createCell(19);
				celda.setCellValue(pro.getSociosEstrategicos());

				celda = headerRow2.createCell(20);
				celda.setCellValue(pro.getRolesParticipantes());

				celda = headerRow2.createCell(21);
				celda.setCellValue(pro.getFinanciacion());

				celda = headerRow2.createCell(22);
				celda.setCellValue(pro.getCobertura());

				String poblacionesString = "";
				// poblaciones
				for (ProyectosPoblacion pre : poblaciones) {

					TipoPoblacion tipoPoblacion = tipoPoblacionService.findById(pre.getPoblacionId());
					poblacionesString += tipoPoblacion.getPoblacion() + ", ";

				}

				celda = headerRow2.createCell(23);
				celda.setCellValue(poblacionesString);

				celda = headerRow2.createCell(24);
				celda.setCellValue(pro.getAntecedentes());

				celda = headerRow2.createCell(25);
				celda.setCellValue(pro.getJustificacion());

				celda = headerRow2.createCell(26);
				celda.setCellValue(pro.getAlcance());

				celda = headerRow2.createCell(27);
				celda.setCellValue(pro.getObjetivoGeneral());

				celda = headerRow2.createCell(28);
				celda.setCellValue(met.getNombre());

				celda = headerRow2.createCell(29);
				celda.setCellValue(historico);

				row++;
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public ByteArrayInputStream createReportXls(List<IdeasProyectos> ideas) throws IOException {

		int columns = 0;

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet("Ideas");

			org.apache.poi.ss.usermodel.Font headerFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
			((org.apache.poi.ss.usermodel.Font) headerFont).setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// Header Row
			org.apache.poi.ss.usermodel.Font titleFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
			((org.apache.poi.ss.usermodel.Font) titleFont).setBold(true);

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setFont(titleFont);

			Row titleRow = sheet.createRow(0);
			Cell celdaTitle = titleRow.createCell(0);
			celdaTitle.setCellValue("Listado de Ideas");
			celdaTitle.setCellStyle(titleCellStyle);

			Row headerRow = sheet.createRow(2);

			int col = 0;
			Cell cell;

			cell = headerRow.createCell(col);
			cell.setCellValue("Nombre Iniciativa / Propuesta");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Descripción Propuesta");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Tipo Propuesta");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Impacto Estimado");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Problemática / Necesidad");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Poblacion");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Focalización de la Población");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Alineacion Plan");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Posibles Fuentes Financiación");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Dependencias Participantes");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Profesional Responsable");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Teléfono Contacto");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Correo electronico");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Dependencia");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Proyectos Ejecutados");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Capacidad Tecnica");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Fecha Radicación");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Año Formulacion");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Estatus");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Fecha Estatus");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Historico");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Observaciones");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Objetivo General");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Duración Total");
			cell.setCellStyle(headerCellStyle);
			col++;

			cell = headerRow.createCell(col);
			cell.setCellValue("Información Adicional");
			cell.setCellStyle(headerCellStyle);
			col++;

			int row = 3;
			int colu = 0;

			for (IdeasProyectos ide : ideas) {

				Row headerRow2 = sheet.createRow(row);

				Cell celda = headerRow2.createCell(0);
				celda.setCellValue(ide.getNombreIdea());

				celda = headerRow2.createCell(1);
				celda.setCellValue(ide.getDescripcionIdea());

				if (ide.getTipoPropuestaId() != null) {
					TiposPropuestas tipo = propuestasService.findById(ide.getTipoPropuestaId());
					celda = headerRow2.createCell(2);
					celda.setCellValue(tipo.getTipoPropuesta());
				} else {
					celda = headerRow2.createCell(2);
					celda.setCellValue("");
				}

				celda = headerRow2.createCell(3);
				celda.setCellValue(ide.getImpacto());

				celda = headerRow2.createCell(4);
				celda.setCellValue(ide.getProblematica());

				celda = headerRow2.createCell(5);
				celda.setCellValue(ide.getPoblacion());

				celda = headerRow2.createCell(6);
				celda.setCellValue(ide.getFocalizacion());

				if (ide.getTipoObjetivoId() != null) {
					TiposObjetivos tip = objetivosService.findById(ide.getTipoObjetivoId());
					celda = headerRow2.createCell(7);
					celda.setCellValue(tip.getDescripcionObjetivo());
				} else {
					celda = headerRow2.createCell(7);
					celda.setCellValue("");
				}

				celda = headerRow2.createCell(8);
				celda.setCellValue(ide.getFinanciacion());

				celda = headerRow2.createCell(9);
				celda.setCellValue(ide.getDependenciasParticipantes());

				celda = headerRow2.createCell(10);
				celda.setCellValue(ide.getPersonaEncargada());

				celda = headerRow2.createCell(11);
				celda.setCellValue(ide.getContactoTelefono());

				celda = headerRow2.createCell(12);
				celda.setCellValue(ide.getContactoEmail());

				if (ide.getDependenciaId() != null) {
					OrganizacionesStrategos org = organizacionService.findById(ide.getDependenciaId());
					celda = headerRow2.createCell(13);
					celda.setCellValue(org.getNombre());
				} else {
					celda = headerRow2.createCell(13);
					celda.setCellValue("");
				}

				celda = headerRow2.createCell(14);
				celda.setCellValue(ide.getProyectosEjecutados());

				if (ide.getCapacidadTecnica() != null) {
					celda = headerRow2.createCell(15);
					celda.setCellValue(ide.getCapacidadTecnica());
				} else {
					celda = headerRow2.createCell(15);
					celda.setCellValue("");
				}

				if (ide.getFechaRadicacion() != null) {
					celda = headerRow2.createCell(16);
					celda.setCellValue(ide.getFechaRadicacion().toString());
				} else {
					celda = headerRow2.createCell(16);
					celda.setCellValue("");
				}

				celda = headerRow2.createCell(17);
				celda.setCellValue(ide.getAnioFormulacion());

				if (ide.getEstatusIdeaId() != null) {
					celda = headerRow2.createCell(18);
					EstatusIdeas est = estatusService.findById(ide.getEstatusIdeaId());
					celda.setCellValue(est.getEstatus());
				} else {
					celda = headerRow2.createCell(18);
					celda.setCellValue("");
				}

				if (ide.getFechaEstatus() != null) {
					celda = headerRow2.createCell(19);
					celda.setCellValue(ide.getFechaEstatus().toString());
				} else {
					celda = headerRow2.createCell(19);
					celda.setCellValue("");
				}

				if (ide.getHistorico() != null) {
					celda = headerRow2.createCell(20);
					if (ide.getHistorico()) {
						celda.setCellValue("Si");
					} else {
						celda.setCellValue("No");
					}
				} else {
					celda = headerRow2.createCell(20);
					celda.setCellValue("");
				}

				if (ide.getObservaciones() != null) {
					celda = headerRow2.createCell(21);
					celda.setCellValue(ide.getObservaciones());
				} else {
					celda = headerRow2.createCell(21);
					celda.setCellValue("");
				}

				if (ide.getObjetivoGeneral() != null) {
					celda = headerRow2.createCell(22);
					celda.setCellValue(ide.getObjetivoGeneral());
				} else {
					celda = headerRow2.createCell(22);
					celda.setCellValue("");
				}

				if (ide.getDuracionTotal() != null) {
					celda = headerRow2.createCell(23);
					celda.setCellValue(ide.getDuracionTotal());
				} else {
					celda = headerRow2.createCell(23);
					celda.setCellValue("");
				}

				if (ide.getInformacionAdicional() != null) {
					celda = headerRow2.createCell(24);
					celda.setCellValue(ide.getInformacionAdicional());
				} else {
					celda = headerRow2.createCell(24);
					celda.setCellValue("");
				}

				row++;
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public ByteArrayInputStream createReportDetallePreproyecto(Proyectos preproyecto) {

		TipoProyectoStrategos tipo = tipoProyectoService.findById(preproyecto.getTipoProyectoId());
		IniciativaEstatusStrategos est = iniciativaEstatusService.findById(preproyecto.getEstatusId());
		TiposObjetivos tip = objetivosService.findById(preproyecto.getTipoObjetivoId());
		MetodologiaStrategos met = metodologiasService.findById(preproyecto.getMetodologiaId());

		List<ProyectosPoblacion> poblaciones = proyectoPoblacionService
				.findAllByProyectoId(preproyecto.getProyectoId());

		OrganizacionesStrategos org = organizacionService.findById(preproyecto.getDependenciaId());
		OrganizacionesStrategos orgLider = organizacionService.findById(preproyecto.getDependenciaLider());

		SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");

		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		Integer costo = Integer.parseInt(preproyecto.getCostoEstimado());

		String fecha = formateadorFecha.format(preproyecto.getFechaRadicacion());
		String fechaEstatus = formateadorFecha.format(preproyecto.getFechaEstatus());
		String fechaConvenioIn = "";
		String fechaConvenioFin = "";
		if (preproyecto.getFechaInicioConvenio() != null)
			fechaConvenioIn = formateadorFecha.format(preproyecto.getFechaInicioConvenio());
		if (preproyecto.getFechaCulminacionConvenio() != null)
			fechaConvenioFin = formateadorFecha.format(preproyecto.getFechaCulminacionConvenio());

		String historico = "";
		if (preproyecto.getHistorico() != null && preproyecto.getHistorico()) {
			historico = "Si";
		} else {
			historico = "No";
		}

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfWriter.getInstance(document, out);
			document.open();
			Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);

			if (preproyecto.getIsPreproyecto()) {
				document.addTitle("Reporte de Pre-Proyecto detalle");
				Paragraph p = new Paragraph("Nombre Pre-Proyecto: " + preproyecto.getNombreProyecto(), font);
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);
			} else {
				document.addTitle("Reporte de Proyecto detalle");
				Paragraph p = new Paragraph("Nombre Proyecto: " + preproyecto.getNombreProyecto(), font);
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);
			}
			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(2);

			float[] medidaCeldas = { 2.40f, 6.70f };
			table.setWidths(medidaCeldas);
			table.setWidthPercentage(100);

			Font fontCelda = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);

			PdfPCell celda = new PdfPCell();

			if (preproyecto.getIdeaId() != null) {
				IdeasProyectos idea = ideasProyectosService.findById(preproyecto.getIdeaId());
				celda.setPhrase(new Phrase("Idea Propuesta", fontCelda));
				table.addCell(celda);
				table.addCell(idea.getNombreIdea());
			} else {
				celda.setPhrase(new Phrase("Idea Propuesta", fontCelda));
				table.addCell(celda);
				table.addCell("");
			}

			celda.setPhrase(new Phrase("Dependencia Responsable", fontCelda));
			table.addCell(celda);
			table.addCell(org.getNombre());

			celda.setPhrase(new Phrase("Profesional Responsable", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getProfesionalResponsable());

			celda.setPhrase(new Phrase("Teléfono Contacto", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getContactoTelefono());

			celda.setPhrase(new Phrase("Correo electronico", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getContactoEmail());

			celda.setPhrase(new Phrase("Fecha Radicación", fontCelda));
			table.addCell(celda);
			table.addCell(fecha);

			celda.setPhrase(new Phrase("Año Formulación", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getAnioFormulacion());

			celda.setPhrase(new Phrase("Tipología Proyecto", fontCelda));
			table.addCell(celda);
			table.addCell(tipo.getNombre());

			celda.setPhrase(new Phrase("Estatus Proyecto", fontCelda));
			table.addCell(celda);
			table.addCell(est.getNombre());

			celda.setPhrase(new Phrase("Fecha Estatus", fontCelda));
			table.addCell(celda);
			table.addCell(fechaEstatus);

			celda.setPhrase(new Phrase("Relación Objetivos", fontCelda));
			table.addCell(celda);
			table.addCell(tip.getDescripcionObjetivo());

			celda.setPhrase(new Phrase("Código BDP", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getCodigoBdp());

			celda.setPhrase(new Phrase("Duración (meses)", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getDuracion().toString());

			celda.setPhrase(new Phrase("Costo Estimado", fontCelda));
			table.addCell(celda);
			table.addCell(formatoNumero.format(costo));

			celda.setPhrase(new Phrase("Dependencia Lider", fontCelda));
			table.addCell(celda);
			table.addCell(orgLider.getNombre());

			celda.setPhrase(new Phrase("Pertinencia", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getPertinencia());

			celda.setPhrase(new Phrase("Problemática / Necesidad ", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getProblematica());

			celda.setPhrase(new Phrase("Dependencias Estratégicas", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getDependenciasEstrategicas());

			celda.setPhrase(new Phrase("Socios Estratégicos", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getSociosEstrategicos());

			celda.setPhrase(new Phrase("Roles Entidades", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getRolesParticipantes());

			celda.setPhrase(new Phrase("Posibles Fuentes Financiación", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getFinanciacion());

			celda.setPhrase(new Phrase("Cobertura", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getCobertura());

			String poblacionesString = "";
			// poblaciones
			for (ProyectosPoblacion pre : poblaciones) {

				TipoPoblacion tipoPoblacion = tipoPoblacionService.findById(pre.getPoblacionId());
				poblacionesString += tipoPoblacion.getPoblacion() + ", ";

			}
			celda.setPhrase(new Phrase("Población Beneficiaria", fontCelda));
			table.addCell(celda);
			table.addCell(poblacionesString);

			celda.setPhrase(new Phrase("Antecedentes", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getAntecedentes());

			celda.setPhrase(new Phrase("Justificación", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getJustificacion());

			celda.setPhrase(new Phrase("Alcance del Proyecto", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getAlcance());

			celda.setPhrase(new Phrase("Objetivo General", fontCelda));
			table.addCell(celda);
			table.addCell(preproyecto.getObjetivoGeneral());

			celda.setPhrase(new Phrase("Metodología", fontCelda));
			table.addCell(celda);
			table.addCell(met.getNombre());

			celda.setPhrase(new Phrase("Historico", fontCelda));
			table.addCell(celda);
			table.addCell(historico);

			if (!preproyecto.getIsPreproyecto()) {

				celda.setPhrase(new Phrase("Cooperante", fontCelda));
				table.addCell(celda);
				if (preproyecto.getCooperante() != null)
					table.addCell(preproyecto.getCooperante());
				else
					table.addCell("");

				celda.setPhrase(new Phrase("Dependencias Convenio", fontCelda));
				table.addCell(celda);
				if (preproyecto.getDependenciasConvenio() != null)
					table.addCell(preproyecto.getDependenciasConvenio());
				else
					table.addCell("");

				celda.setPhrase(new Phrase("Número Convenio", fontCelda));
				table.addCell(celda);
				if (preproyecto.getNumeroConvenio() != null)
					table.addCell(preproyecto.getNumeroConvenio());
				else
					table.addCell("");

				celda.setPhrase(new Phrase("Fecha Inicio Convenio", fontCelda));
				table.addCell(celda);
				table.addCell(fechaConvenioIn);

				celda.setPhrase(new Phrase("Fecha Culminación Convenio", fontCelda));
				table.addCell(celda);
				table.addCell(fechaConvenioFin);

				celda.setPhrase(new Phrase("Nombre Operador", fontCelda));
				table.addCell(celda);
				if (preproyecto.getNombreOperador() != null)
					table.addCell(preproyecto.getNombreOperador());
				else
					table.addCell("");

				celda.setPhrase(new Phrase("Contacto Email Operador", fontCelda));
				table.addCell(celda);
				if (preproyecto.getContactoEmailOperador() != null)
					table.addCell(preproyecto.getContactoEmailOperador());
				else
					table.addCell("");

				celda.setPhrase(new Phrase("Contacto Telefono Operador", fontCelda));
				table.addCell(celda);
				if (preproyecto.getContactoTelefonoOperador() != null)
					table.addCell(preproyecto.getContactoTelefonoOperador());
				else
					table.addCell("");

				celda.setPhrase(new Phrase("Recursos Asignados", fontCelda));
				table.addCell(celda);
				if (preproyecto.getRecursosAsignados() != null)
					table.addCell(preproyecto.getRecursosAsignados());
				else
					table.addCell("");

				celda.setPhrase(new Phrase("Prorrogas", fontCelda));
				table.addCell(celda);
				if (preproyecto.getProrrogas() != null)
					table.addCell(preproyecto.getProrrogas());
				else
					table.addCell("");
			}

			document.add(table);
			document.add(Chunk.NEWLINE);

			document.close();

		} catch (DocumentException ex) {

			System.out.println("error");
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream createReportDetalle(IdeasProyectos idea) {

		TiposPropuestas tip = propuestasService.findById(idea.getTipoPropuestaId());
		TiposObjetivos tipo = objetivosService.findById(idea.getTipoObjetivoId());
		OrganizacionesStrategos org = organizacionService.findById(idea.getDependenciaId());

		EstatusIdeas est = estatusService.findById(idea.getEstatusIdeaId());
		SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = formateadorFecha.format(idea.getFechaRadicacion());
		String fechaEstatus = formateadorFecha.format(idea.getFechaEstatus());
		String historico = "";
		if (idea.getHistorico() != null && idea.getHistorico()) {
			historico = "Si";
		} else {
			historico = "No";
		}

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfWriter.getInstance(document, out);
			document.open();

			document.addTitle("Reporte de ideas detalle");
			Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
			Paragraph p = new Paragraph("Nombre Iniciativa / Propuesta: " + idea.getNombreIdea(), font);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(2);

			Font fontCelda = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);

			PdfPCell celda = new PdfPCell();

			celda.setPhrase(new Phrase("Descripción Propuesta", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getDescripcionIdea());

			celda.setPhrase(new Phrase("Tipo Propuesta", fontCelda));
			table.addCell(celda);
			table.addCell(tip.getTipoPropuesta());

			celda.setPhrase(new Phrase("Impacto Estimado", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getImpacto());

			celda.setPhrase(new Phrase("Problemática / Necesidad", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getProblematica());

			celda.setPhrase(new Phrase("Poblacion", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getPoblacion());

			celda.setPhrase(new Phrase("Focalización de la Población", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getFocalizacion());

			celda.setPhrase(new Phrase("Alineacion Plan", fontCelda));
			table.addCell(celda);
			table.addCell(tipo.getDescripcionObjetivo());

			celda.setPhrase(new Phrase("Posibles Fuentes Financiación", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getFinanciacion());

			celda.setPhrase(new Phrase("Dependencia Participantes", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getDependenciasParticipantes());

			celda.setPhrase(new Phrase("Profesional Responsable", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getPersonaEncargada());

			celda.setPhrase(new Phrase("Teléfono Contacto", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getContactoTelefono());

			celda.setPhrase(new Phrase("Correo electronico", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getContactoEmail());

			celda.setPhrase(new Phrase("Dependencia", fontCelda));
			table.addCell(celda);
			table.addCell(org.getNombre());

			celda.setPhrase(new Phrase("Proyectos Ejecutados", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getProyectosEjecutados());

			celda.setPhrase(new Phrase("Capacidad Tecnica", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getCapacidadTecnica());

			celda.setPhrase(new Phrase("Fecha Radicación", fontCelda));
			table.addCell(celda);
			table.addCell(fecha);

			celda.setPhrase(new Phrase("Año Formulacion", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getAnioFormulacion());

			celda.setPhrase(new Phrase("Estatus", fontCelda));
			table.addCell(celda);
			table.addCell(est.getEstatus());

			celda.setPhrase(new Phrase("Fecha Estatus", fontCelda));
			table.addCell(celda);
			table.addCell(fechaEstatus);

			celda.setPhrase(new Phrase("Historico", fontCelda));
			table.addCell(celda);
			table.addCell(historico);

			celda.setPhrase(new Phrase("Observaciones", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getObservaciones());

			celda.setPhrase(new Phrase("Objetivo General", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getObjetivoGeneral());

			celda.setPhrase(new Phrase("Duración Total", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getDuracionTotal());

			celda.setPhrase(new Phrase("Información Adicional", fontCelda));
			table.addCell(celda);
			table.addCell(idea.getInformacionAdicional());

			document.add(table);
			document.add(Chunk.NEWLINE);

			document.close();

		} catch (DocumentException ex) {

			System.out.println("error");
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream createReportPreproyecto(List<Proyectos> proyectos) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfWriter.getInstance(document, out);
			document.open();

			document.addTitle("Reporte proyectos resumido");
			Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
			Paragraph p = new Paragraph("Listado resumido de Proyectos", font);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(6);

			float[] medidaCeldas = { 1.0f, 6.70f, 2.10f, 2.10f, 1.60f, 2.10f };
			table.setWidths(medidaCeldas);
			table.setWidthPercentage(100);

			addTableHeaderPreproyecto(table);

			for (Proyectos pre : proyectos) {
				addRowsPreproyecto(table, pre);
			}

			document.add(table);
			document.close();

		} catch (DocumentException ex) {

			System.out.println("error");
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream createReportePlanPdf(List<PerspectivaStrategos> perspectivas) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PerspectivaStrategos padre = new PerspectivaStrategos();
		List<PerspectivaStrategos> objetivos = new ArrayList();

		try {
			PdfWriter.getInstance(document, out);
			document.open();

			document.addTitle("Reporte Plan resumido");
			Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD);
			Paragraph p = new Paragraph("Listado resumido del Plan", font);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			document.add(Chunk.NEWLINE);

			for (PerspectivaStrategos per : perspectivas) {
				if (per.getPadreId() == null) {
					padre = per;
				}
			}

			for (PerspectivaStrategos per : perspectivas) {
				if (per.getPadreId() == padre.getPerspectivaId()) {
					objetivos.add(per);
				}
			}

			font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
			Paragraph SubTitulo = new Paragraph("Proyecto : " + padre.getNombre(), font);
			SubTitulo.setAlignment(Element.ALIGN_LEFT);
			document.add(SubTitulo);

			document.add(Chunk.SPACETABBING);

			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(90);
			Font fontCelda = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
			Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
			PdfPCell celda = new PdfPCell();

			Stream.of("Objetivos").forEach(columnTitle -> {
				PdfPCell header = new PdfPCell();
				header.setBackgroundColor(BaseColor.BLUE);
				header.setHorizontalAlignment(1);
				header.setBorderWidth(1);
				header.setPhrase(new Phrase(columnTitle, fontHeader));
				table.addCell(header);
			});

			Font fontObj = new Font(FontFamily.HELVETICA, 11, Font.NORMAL);
			Paragraph Objetivo = new Paragraph();
			for (PerspectivaStrategos obj : objetivos) {

				celda.setPhrase(new Phrase(obj.getNombre(), fontCelda));
				table.addCell(celda);
			}
			document.add(table);

			for (PerspectivaStrategos obj : objetivos) {
				document.add(Chunk.NEWLINE);
				List<PerspectivaStrategos> productos = new ArrayList();
				Objetivo = new Paragraph(" Objetivo : " + obj.getNombre(), font);
				Objetivo.setAlignment(Element.ALIGN_LEFT);
				document.add(Objetivo);

				for (PerspectivaStrategos per : perspectivas) {
					if (per.getPadreId() == obj.getPerspectivaId()) {
						productos.add(per);
					}
				}
				Font fontTit = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLUE);
				Paragraph proText = new Paragraph();
				for (PerspectivaStrategos pro : productos) {
					document.add(Chunk.SPACETABBING);
					Paragraph titulo = new Paragraph("Producto", fontTit);
					document.add(titulo);
					proText = new Paragraph(pro.getNombre(), fontObj);
					proText.setAlignment(Element.ALIGN_LEFT);
					document.add(proText);
					addRowsObj(pro.getPerspectivaId(), document);
					document.add(Chunk.TABBING);
				}

			}

			document.add(Chunk.NEWLINE);

			document.close();

		} catch (DocumentException ex) {

			System.out.println("error");
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream createReportePlanXls(List<PerspectivaStrategos> perspectivas) throws IOException {
		PerspectivaStrategos padre = new PerspectivaStrategos();
		List<PerspectivaStrategos> objetivos = new ArrayList();

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			for (PerspectivaStrategos per : perspectivas) {
				if (per.getPadreId() == null) {
					padre = per;
				}
			}

			for (PerspectivaStrategos per : perspectivas) {
				if (per.getPadreId() == padre.getPerspectivaId()) {
					objetivos.add(per);
				}
			}

			Sheet sheet = workbook.createSheet("Plan");

			org.apache.poi.ss.usermodel.Font headerFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
			((org.apache.poi.ss.usermodel.Font) headerFont).setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// Header Row
			org.apache.poi.ss.usermodel.Font titleFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
			((org.apache.poi.ss.usermodel.Font) titleFont).setBold(true);

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setFont(titleFont);

			Row titleRow = sheet.createRow(0);
			Cell celdaTitle = titleRow.createCell(0);
			celdaTitle.setCellValue("Listado Resumido del plan");
			celdaTitle.setCellStyle(titleCellStyle);

			Row headerRow = sheet.createRow(2);

			int col = 0;
			Cell cell;

			cell = headerRow.createCell(col);
			cell.setCellValue("Nombre Plan");
			cell.setCellStyle(headerCellStyle);

			int row = 3;
			int colu = 0;

			Row headerRow2 = sheet.createRow(row);
			Cell celda = headerRow2.createCell(0);
			celda.setCellValue(padre.getNombre());

			row = row + 2;
			Row headerRow3 = sheet.createRow(row);
			Cell celda3 = headerRow3.createCell(0);
			celda3.setCellValue("Objetivos");
			celda3.setCellStyle(headerCellStyle);

			row++;
			for (PerspectivaStrategos obj : objetivos) {
				Row headerRow4 = sheet.createRow(row);
				Cell celda4 = headerRow4.createCell(0);
				celda4.setCellValue(obj.getNombre());
				row++;
			}

			for (PerspectivaStrategos obj : objetivos) {
				row = row + 2;
				List<PerspectivaStrategos> productos = new ArrayList();
				Row headerRow4 = sheet.createRow(row);
				Cell celda4 = headerRow4.createCell(0);
				celda4.setCellValue("Objetivo - " + obj.getNombre());
				row++;

				for (PerspectivaStrategos per : perspectivas) {
					if (per.getPadreId() == obj.getPerspectivaId()) {
						productos.add(per);
					}
				}

				row = row + 1;
				for (PerspectivaStrategos pro : productos) {
					List<IndicadorPerspectiva> indicadores = new ArrayList();
					List<IniciativaPerspectiva> tareas = new ArrayList();
					Row headerRow6 = sheet.createRow(row);
					Cell celda5 = headerRow6.createCell(0);
					celda5.setCellValue("Producto - " + pro.getNombre());
					indicadores = indicadorPerspectivaService.findByPerspectiva(pro.getPerspectivaId());
					tareas = iniciativaPerspectivaService.findByPerspectiva(pro.getPerspectivaId());
					if (indicadores.size() > 0) {
						row = row+2;
						int columna = 0;
						Row headerRow5 = sheet.createRow(row);
						Cell cell1;
						cell1 = headerRow5.createCell(columna);
						cell1.setCellValue("Nombre Indicador");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						cell1 = headerRow5.createCell(columna);
						cell1.setCellValue("Alerta");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						cell1 = headerRow5.createCell(columna);
						cell1.setCellValue("Unidad");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						cell1 = headerRow5.createCell(columna);
						cell1.setCellValue("Ejecutado");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						cell1 = headerRow5.createCell(columna);
						cell1.setCellValue("Meta");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						cell1 = headerRow5.createCell(columna);
						cell1.setCellValue("% Cumplimiento");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						cell1 = headerRow5.createCell(columna);
						cell1.setCellValue("Ultima medicion");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						row++;
						for (IndicadorPerspectiva ind : indicadores) {
							IndicadorStrategos indicador = indicadorService.findById(ind.getPk().getIndicadorId());
							Row headerRow7 = sheet.createRow(row);

							Cell celda6 = headerRow7.createCell(0);
							celda6.setCellValue(indicador.getNombre());

							if (indicador.getAlerta() == 3) {
								celda6 = headerRow7.createCell(1);
								celda6.setCellValue("Rojo");
							} else if (indicador.getAlerta() == 2) {
								celda6 = headerRow7.createCell(1);
								celda6.setCellValue("Amarillo");
							} else if (indicador.getAlerta() == 1) {
								celda6 = headerRow7.createCell(1);
								celda6.setCellValue("Verde");
							}
							celda6 = headerRow7.createCell(2);
							celda6.setCellValue(indicador.getUnidadId());

							celda6 = headerRow7.createCell(3);
							celda6.setCellValue(indicador.getUltimaMedicion());

							celda6 = headerRow7.createCell(4);
							celda6.setCellValue(indicador.getUltimoProgramado());

							celda6 = headerRow7.createCell(5);
							celda6.setCellValue(indicador.getPorcentajeCumplimiento());

							celda6 = headerRow7.createCell(6);
							celda6.setCellValue(indicador.getFechaUltimaMedicion());

						}
					}

					if (tareas.size() > 0) {
						row = row + 2;
						int columna = 0;
						Row headerRow7 = sheet.createRow(row);
						Cell cell1;
						cell1 = headerRow7.createCell(columna);
						cell1.setCellValue("Nombre Tarea");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						cell1 = headerRow7.createCell(columna);
						cell1.setCellValue("Alerta");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						cell1 = headerRow7.createCell(columna);
						cell1.setCellValue("% Completado");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						cell1 = headerRow7.createCell(columna);
						cell1.setCellValue("% Programado");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						cell1 = headerRow7.createCell(columna);
						cell1.setCellValue("Ultima medicion");
						cell1.setCellStyle(headerCellStyle);
						columna++;

						row++;
						for (IniciativaPerspectiva tar : tareas) {
							Iniciativa tarea = iniciativaService.findById(tar.getIniciativaId());
							Row headerRow8 = sheet.createRow(row);

							Cell celda6 = headerRow8.createCell(0);
							celda6.setCellValue(tarea.getNombreIniciativa());

							if (tarea.getAlerta() != null) {
								if (tarea.getAlerta() == 3) {
									celda6 = headerRow8.createCell(1);
									celda6.setCellValue("Rojo");
								} else if (tarea.getAlerta() == 2) {
									celda6 = headerRow8.createCell(1);
									celda6.setCellValue("Amarillo");
								} else if (tarea.getAlerta() == 1) {
									celda6 = headerRow8.createCell(1);
									celda6.setCellValue("Verde");
								}
							}

							if (tarea.getUltimaMedicion() != null) {
								celda6 = headerRow8.createCell(2);
								celda6.setCellValue(tarea.getUltimaMedicion());
							}else {
								celda6 = headerRow8.createCell(2);
								celda6.setCellValue("");
							}

							if (tarea.getUltimoProgramado() != null) {
								celda6 = headerRow8.createCell(3);
								celda6.setCellValue(tarea.getUltimoProgramado());
							}else {
								celda6 = headerRow8.createCell(2);
								celda6.setCellValue("");
							}							

							if (tarea.getFechaUltimaMedicion() != null) {
								celda6 = headerRow8.createCell(4);
								celda6.setCellValue(tarea.getFechaUltimaMedicion());
							}else {
								celda6 = headerRow8.createCell(2);
								celda6.setCellValue("");
							}
						}
					}

					row = row + 2;
				}
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public void addTableHeader(PdfPTable table) {

		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
		Stream.of("Año", "Idea Proyecto", "Dependencia", "Tipo Propuesta", "Estatus").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.BLUE);
			header.setBorderWidth(1);
			header.setPhrase(new Phrase(columnTitle, font));
			table.addCell(header);
		});
	}

	public void addRows(PdfPTable table, IdeasProyectos idea) {

		OrganizacionesStrategos org = organizacionService.findById(idea.getDependenciaId());
		TiposPropuestas tip = propuestasService.findById(idea.getTipoPropuestaId());
		EstatusIdeas est = estatusService.findById(idea.getEstatusIdeaId());

		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
		Stream.of(idea.getAnioFormulacion(), idea.getNombreIdea(), org.getNombre(), tip.getTipoPropuesta(),
				est.getEstatus()).forEach(columnas -> {
					PdfPCell header = new PdfPCell();
					header.setBorderWidth(1);
					header.setPhrase(new Phrase(columnas, font));
					table.addCell(header);
				});

	}

	public void addTableHeaderPreproyecto(PdfPTable table) {

		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
		Stream.of("Año", "Proyecto", "Dependencia", "Tipología", "Estatus", "Costo Estimado").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.BLUE);
			header.setBorderWidth(1);
			header.setPhrase(new Phrase(columnTitle, font));
			table.addCell(header);
		});
	}

	public void addRowsPreproyecto(PdfPTable table, Proyectos proyecto) {

		OrganizacionesStrategos org = organizacionService.findById(proyecto.getDependenciaId());
		TipoProyectoStrategos tip = tipoProyectoService.findById(proyecto.getTipoProyectoId());
		IniciativaEstatusStrategos est = iniciativaEstatusService.findById(proyecto.getEstatusId());
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		Integer costo = Integer.parseInt(proyecto.getCostoEstimado());

		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
		Stream.of(proyecto.getAnioFormulacion(), proyecto.getNombreProyecto(), org.getNombre(), tip.getNombre(),
				est.getNombre(), formatoNumero.format(costo)).forEach(columnas -> {
					PdfPCell header = new PdfPCell();
					header.setBorderWidth(1);
					header.setPhrase(new Phrase(columnas, font));
					table.addCell(header);
				});

	}

	public List<String> ordenarIdeas(List<IdeasEvaluadas> ideasEvaluadas) {
		List<String> puntajes = new ArrayList<>();

		for (IdeasEvaluadas ide : ideasEvaluadas) {
			IdeasProyectos idea = ideasProyectosService.findById(ide.getIdeaId());
			if (idea.getValorUltimaEvaluacion() != null) {
				puntajes.add(idea.getValorUltimaEvaluacion().toString() + "-" + idea.getIdeaId());
			} else {
				puntajes.add("0" + "-" + idea.getIdeaId());
			}

		}

		System.out.println("Before Sorting: " + puntajes);

		Collections.sort(puntajes, Collections.reverseOrder());

		System.out.println("After Sorting: " + puntajes);

		return puntajes;
	}

	public void addRowsObj(Long productoId, Document document) {
		Paragraph producto = new Paragraph();

		try {
			Font fontObj = new Font(FontFamily.HELVETICA, 11, Font.NORMAL);

			List<IndicadorPerspectiva> indicadores = new ArrayList();
			List<IniciativaPerspectiva> tareas = new ArrayList();

			indicadores = indicadorPerspectivaService.findByPerspectiva(productoId);
			tareas = iniciativaPerspectivaService.findByPerspectiva(productoId);
			Font fontTit = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.RED);

			DecimalFormat format = new DecimalFormat("#.00");

			// Tabla Indicadores

			if (indicadores.size() > 0) {
				PdfPTable table = new PdfPTable(7);
				Font fontCelda = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
				PdfPCell celda = new PdfPCell();

				float[] medidaCeldas = { 6.70f, 1.70f, 2.10f, 1.90f, 1.40f, 2.40f, 2f };
				table.setWidths(medidaCeldas);
				table.setWidthPercentage(90);

				Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
				Stream.of("Nombre Indicador", "Alerta", "Unidad", "Ejecutado", "Meta", "% Cumplimiento",
						"Ultima medicion").forEach(columnTitle -> {
							PdfPCell header = new PdfPCell();
							header.setBackgroundColor(BaseColor.BLUE);
							header.setBorderWidth(1);
							header.setPhrase(new Phrase(columnTitle, font));
							table.addCell(header);
						});

				for (IndicadorPerspectiva ind : indicadores) {
					IndicadorStrategos indicador = indicadorService.findById(ind.getPk().getIndicadorId());
					celda.setPhrase(new Phrase(indicador.getNombre(), fontCelda));
					table.addCell(celda);

					if (indicador.getAlerta() == 3) {
						celda.setPhrase(new Phrase("Rojo", fontCelda));
						table.addCell(celda);
					} else if (indicador.getAlerta() == 2) {
						celda.setPhrase(new Phrase("Amarillo", fontCelda));
						table.addCell(celda);
					} else if (indicador.getAlerta() == 1) {
						celda.setPhrase(new Phrase("Verde", fontCelda));
						table.addCell(celda);
					}
					celda.setPhrase(new Phrase(indicador.getUnidadId().toString(), fontCelda));
					table.addCell(celda);
					celda.setPhrase(new Phrase(indicador.getUltimaMedicion().toString(), fontCelda));
					table.addCell(celda);
					celda.setPhrase(new Phrase(indicador.getUltimoProgramado().toString(), fontCelda));
					table.addCell(celda);
					celda.setPhrase(
							new Phrase(format.format(indicador.getPorcentajeCumplimiento()).toString(), fontCelda));
					table.addCell(celda);
					celda.setPhrase(new Phrase(indicador.getFechaUltimaMedicion(), fontCelda));
					table.addCell(celda);

				}

				document.add(Chunk.SPACETABBING);
				document.add(table);
			} else {
				document.add(Chunk.TABBING);
				producto = new Paragraph(" No se encontraron Indicadores asignados ", fontTit);
				producto.setAlignment(Element.ALIGN_CENTER);
				document.add(producto);
			}

			// Tabla Tareas

			if (tareas.size() > 0) {
				PdfPTable table2 = new PdfPTable(5);
				PdfPCell celda2 = new PdfPCell();
				Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
				Font fontCelda = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
				float[] medidaCeldas2 = { 6.70f, 1.70f, 2.10f, 2.10f, 1.60f };
				table2.setWidths(medidaCeldas2);
				table2.setWidthPercentage(90);

				Stream.of("Nombre Actividad", "Alerta", "% Completado", "% Programado", "Ultima Medicion")
						.forEach(columnTitle -> {
							PdfPCell header = new PdfPCell();
							header.setBackgroundColor(BaseColor.BLUE);
							header.setBorderWidth(1);
							header.setPhrase(new Phrase(columnTitle, font));
							table2.addCell(header);
						});

				for (IniciativaPerspectiva tar : tareas) {
					Iniciativa tarea = iniciativaService.findById(tar.getIniciativaId());

					celda2.setPhrase(new Phrase(tarea.getNombreIniciativa(), fontCelda));
					table2.addCell(celda2);
					if (tarea.getAlerta() != null) {
						if (tarea.getAlerta() == 3) {
							celda2.setPhrase(new Phrase("Rojo", fontCelda));
							table2.addCell(celda2);
						} else if (tarea.getAlerta() == 2) {
							celda2.setPhrase(new Phrase("Amarillo", fontCelda));
							table2.addCell(celda2);
						} else if (tarea.getAlerta() == 1) {
							celda2.setPhrase(new Phrase("Verde", fontCelda));
							table2.addCell(celda2);
						}
					} else {
						celda2.setPhrase(new Phrase("", fontCelda));
						table2.addCell(celda2);
					}
					if (tarea.getUltimaMedicion() != null) {
						celda2.setPhrase(new Phrase(tarea.getUltimaMedicion().toString(), fontCelda));
						table2.addCell(celda2);
					} else {
						celda2.setPhrase(new Phrase("", fontCelda));
						table2.addCell(celda2);
					}

					if (tarea.getUltimoProgramado() != null) {
						celda2.setPhrase(new Phrase(tarea.getUltimoProgramado().toString(), fontCelda));
						table2.addCell(celda2);
					} else {
						celda2.setPhrase(new Phrase("", fontCelda));
						table2.addCell(celda2);
					}

					if (tarea.getFechaUltimaMedicion() != null) {
						celda2.setPhrase(new Phrase(tarea.getFechaUltimaMedicion(), fontCelda));
						table2.addCell(celda2);
					} else {
						celda2.setPhrase(new Phrase("", fontCelda));
						table2.addCell(celda2);
					}

				}
				document.add(Chunk.TABBING);
				document.add(table2);
			} else {
				document.add(Chunk.TABBING);
				producto = new Paragraph(" No se encontraron Tareas asignados ", fontTit);
				producto.setAlignment(Element.ALIGN_CENTER);
				document.add(producto);
			}

		} catch (DocumentException ex) {

			System.out.println("error");
		}
	}

	public Double acumularPresupuesto() {

		Double valor = 0.0;

		return valor;
	}

}
