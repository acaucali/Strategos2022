INSERT INTO iniciativa_plan (iniciativa_id, plan_id)
SELECT iniciativa_id, plan_id FROM iniciativa_por_plan;