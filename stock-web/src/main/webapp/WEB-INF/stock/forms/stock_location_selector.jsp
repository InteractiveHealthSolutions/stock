 Province

<select id="province" name="province" bind-value="${province}"
	onchange="loadLocation('division', this.value, ['division', 'city', 'town', 'uc', 'vaccination center']);">
	<option value="${province}">${province}</option>
</select>

Division
<select id="division" name="division" bind-value="${division}"
	onchange="loadLocation('city', this.value, ['city', 'town', 'uc', 'vaccination center']);">
	<option value="${division}">${division}</option>
</select>

District
<select id="city" name="city" bind-value="${city}"
	onchange="loadLocation('town', this.value, ['town', 'uc', 'vaccination center']);">
	<option value="${city}">${city}</option>
</select>

Town
<select id="town" name="town" bind-value="${town}"
	onchange="loadLocation('uc', this.value, ['uc', 'vaccination center']);">
	<option value="${town}">${town}</option>
</select>

UC
<select id="uc" name="uc" bind-value="${uc}"
	onchange="loadLocation('vaccination center', this.value);">
	<option value="${uc}">${uc}</option>
</select>

Center
<select id="vaccinationcenter" name="vaccinationcenter"
	bind-value="${vaccinationcenter}">
	<option value="${vaccinationcenter}">${vaccinationcenter}</option>
</select>

<script>
$(document).ready(function() {
    DWRLocationService.getLocationList(["province"],null,{
         async: false,
                  callback: function (resl) {
             $('#province').empty().append('<option></option>');
             for ( var i = 0; i < resl.length; i++) {
                     $('#province').append('<option value="'+resl[i].locationId+'">'+resl[i].fullname+'</option>');
             }
             $('#province').val($('#province').attr('bind-value'));
             if($('#province').val() != ''){
					loadDivision();
					console.log("DWR ");

           	 }
    }});
});

  function loadDivision(){
    DWRLocationService.getLocationList(["division"],$('#province').val(),{
        async: false,
        callback: function (resl) {
            $('#division').empty().append('<option></option>');
            for ( var i = 0; i < resl.length; i++) {
                    $('#division').append('<option value="'+resl[i].locationId+'">'+resl[i].fullname+'</option>');
            }
            $('#division').val($('#division').attr('bind-value'));
            if($('#division').val() != ''){
				loadDistrict();
       	 }
   }});
  }
  
  function loadDistrict(){
	    DWRLocationService.getLocationList(["city"],$('#division').val(),{
	        async: false,
	        callback: function (resl) {
	            $('#city').empty().append('<option></option>');
	            for ( var i = 0; i < resl.length; i++) {
	                    $('#city').append('<option value="'+resl[i].locationId+'">'+resl[i].fullname+'</option>');
	            }
	            $('#city').val($('#city').attr('bind-value'));
	            if($('#city').val() != ''){
					loadTown();
	       	 }
	   }});
	  }
  

  function loadTown(){
	    DWRLocationService.getLocationList(["town"],$('#city').val(),{
	        async: false,
	        callback: function (resl) {
	            $('#town').empty().append('<option></option>');
	            for ( var i = 0; i < resl.length; i++) {
	                    $('#town').append('<option value="'+resl[i].locationId+'">'+resl[i].fullname+'</option>');
	            }
	            $('#town').val($('#town').attr('bind-value'));
	            if($('#town').val() != ''){
					loadUc();
	       	 }
	   }});
	}
  
  function loadUc(){
	    DWRLocationService.getLocationList(["UC"],$('#town').val(),{
	        async: false,
	        callback: function (resl) {
	            $('#uc').empty().append('<option></option>');
	            for ( var i = 0; i < resl.length; i++) {
	                    $('#uc').append('<option value="'+resl[i].locationId+'">'+resl[i].fullname+'</option>');
	            }
	            $('#uc').val($('#uc').attr('bind-value'));
	            if($('#uc').val() != ''){
					loadCenter();
	       	 }
	   }});
	 }
  
  function loadCenter(){
	    DWRLocationService.getLocationList(["Vaccination Center"],$('#uc').val(),{
	        async: false,
	        callback: function (resl) {
	            $('#vaccinationcenter').empty().append('<option></option>');
	            for ( var i = 0; i < resl.length; i++) {
	                    $('#vaccinationcenter').append('<option value="'+resl[i].locationId+'">'+resl[i].fullname+'</option>');
	            }
	            $('#vaccinationcenter').val($('#vaccinationcenter').attr('bind-value'));

	   }});
	 }
function loadLocation(tag, parentId, resetFields) {
	DWRLocationService.getLocationList([tag], parentId, {
		async: false,
		callback: function (resl) {
			if(resetFields){
				for (var i = 0; i < resetFields.length; i++) {
					resetId = '#'+resetFields[i].replace(' ', '');
					$(resetId).empty().append('<option></option>');
				}
			}
			
			childid = '#'+tag.replace(' ', '');
			$(childid).empty().append('<option></option>');
			for ( var i = 0; i < resl.length; i++) {
				$(childid).append('<option value="'+resl[i].locationId+'">'+resl[i].name+'</option>');
			}
	}});
}
</script>

