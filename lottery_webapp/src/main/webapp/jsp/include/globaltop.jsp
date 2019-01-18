<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="error_msg_modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body ">
				<span id="error_content" class="text-danger"> ${errorMsg }</span>
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
			</div>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		if ('${errorMsg }' != "") {
			$("#error_msg_modal").modal("show");
			$("#error_msg_modal").css({
				top : "40%"
			});
		}
	});
</script>
