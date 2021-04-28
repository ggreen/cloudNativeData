
drop table if exists trace;
create table trace (	
	eqpIndex	text,
	unitIndex	text,
	paramIndex	text,
	lotId		text,
	ppId		text,
	recipeId	text,
	stepSeq		text,
	pairId		text,
	processId	text,
	waferId		text,
	waferNo		int,
	lotType		text,
	statusTf	boolean,
	ts			text,
	vl			text,
	ls			text,
	us			text,
	sl			text
) distributed by (eqpIndex, unitIndex, paramIndex, lotId, ppId, recipeId, stepSeq, pairId, processId, 
	waferId, waferNo, lotType, statusTf);		
