$(document).ready(function () {

    $("#related_symptoms").css('visibility', 'hidden');

    $('#search_selection').change(function () {
        $('div.response').css('display', 'none');
        $('div.posts').css('display', 'none');
        $('div.posts_title').css('display', 'none');
        $('div.disease_title').css('display', 'none');
        $('div.drug_title').css('display', 'none');
        $('div.drug').css('display', 'none');
    })

    // add event listener for Enter button to invoke search
    var input = document.getElementById("search_box");
    input.addEventListener("keyup", function(event) {
        // Number 13 is the "Enter" key on the keyboard
        if (event.keyCode === 13) {
            event.preventDefault();
            // Trigger the button element with a click
            document.getElementById("search_button").click();
        }
    });

})

function sendSearchRequest() {
    var searchQuery = $('#search_box').val();
    searchQuery = searchQuery.trim();
    const selectedSearchOption = document.getElementById("search_selection").value;
    // empty search is not allowed, show alert
    if (searchQuery === '') {
        if (selectedSearchOption === 'disease_search') {
            swal( "Empty Search Query" ,  "Please type the symptoms you want to search." ,  "error" );
        }
        else if (selectedSearchOption === 'drug_search') {
            swal("Empty Search Query", "Please type the disease name you want to search.", "error");
        }
    }

    ajaxGetRequest(window.location.href + '/search?query=' + searchQuery + '&type=' + selectedSearchOption, handleSearchResponse)
}

function handleSearchResponse(response) {
    // var res = {
    //     "query": "shortness of breath",
    //
    //     "annotated_query_terms": {
    //         "symptoms": [
    //             "dyspnea"
    //         ]
    //     },
    //
    //     "results": {
    //         "mayo_clinic": [
    //             {
    //                 "title": "Hantavirus-Pulmonary-Syndrome",
    //                 "url": "https://www.mayoclinic.org/diseases-conditions/hantavirus-pulmonary-syndrome/symptoms-causes/syc-20351838",
    //                 "disease": "hantavirus-pulmonary-syndrome",
    //                 "symptoms": [
    //                     "Fever and chills",
    //                     "Headaches and muscle aches",
    //                     "A cough that produces secretions",
    //                     "Shortness of breath",
    //                     "Fluid accumulating within the lungs",
    //                     "Low blood pressure",
    //                     "Reduced heart efficiency"
    //                 ],
    //                 "summary": "\"Hantavirus pulmonary syndrome advances through two distinct stages. this may or may not be there, if available it is just like Google search result summary/snippet"
    //             },
    //             {
    //                 "title": "Shortness of Breath",
    //                 "disease": "",
    //                 "url": "https://www.mayoclinic.org/diseases-conditions/amniotic-fluid-embolism/symptoms-causes/syc-20369324",
    //                 "other_symptoms": [
    //                     "Chills",
    //                     "Rapid heart rate or disturbances in the rhythm of the heart rate",
    //                     "Fetal distress, such as a slow heart rate, or other fetal heart rate abnormalities",
    //                     "Seizures",
    //                     "Loss of consciousness"
    //                 ],
    //                 "summary": "this may or may not be there, if available it is just like Google search result summary/snippet"
    //             }
    //         ],
    //
    //         "web_md_mb": [
    //             {
    //                 "title": "Blood Sugar diet",
    //                 "url": "https://messageboards.webmd.com/health-conditions/f/diabetes/91517/blood-sugar-diet",
    //                 "disease": "this will be there if we can ascertain disease name from the post, otherwise will be empty string",
    //                 "other_symptoms": [
    //                     "abc",
    //                     "def"
    //                 ],
    //                 "treatments": [
    //                     "abc",
    //                     "def",
    //                     "this treatments will be returned only if post contains some, otherwise it will be empty list"
    //                 ],
    //                 "summary": "I found out I had T2 diabetes in 2012. I've just started the (Michael Mosley) 8-week blood sugar diet - I discovered it after reading Fixing Dad. For those who don't know about it, it's a low-carb, low calorie program that is based on the Mediterranean diet."
    //             },
    //             {
    //                 "title": "Am I at risk of getting diabetes?",
    //                 "disease": "Diabetes",
    //                 "url": "https://messageboards.webmd.com/health-conditions/f/diabetes/105280/am-i-at-risk-of-getting-diabetes",
    //                 "other_symptoms": [
    //                     "abc",
    //                     "def"
    //                 ],
    //                 "treatments": [
    //                     "abc",
    //                     "def",
    //                     "this treatments will be returned only if post contains some, otherwise it will be empty list"
    //                 ],
    //                 "summary": "I'm a 21 year old guy and weigh about 350 pounds, so a little overweight."
    //             }
    //         ],
    //
    //         "patient_info": [
    //             {
    //                 "title": "Persistent change in bowel habits and abdominal discomfort.",
    //                 "disease": "disorder",
    //                 "url": "https://patient.info/forums/discuss/persistent-change-in-bowel-habits-and-abdominal-discomfort--733001",
    //                 "other_symptoms": [
    //                     "abc",
    //                     "def"
    //                 ],
    //                 "treatments": [
    //                     "abc",
    //                     "def",
    //                     "this treatments will be returned only if post contains some, otherwise it will be empty list"
    //                 ],
    //                 "summary": "Hi, i'm an 18 year old male. I wish i could keep it shorter than this but i felt the need to mention all this. A couple months."
    //             },
    //             {
    //                 "title": "CORONA VIRUS SPRAY",
    //                 "disease": "disorder",
    //                 "url": "https://patient.info/forums/discuss/corona-virus-spray-733013",
    //                 "other_symptoms": [
    //                     "abc",
    //                     "def"
    //                 ],
    //                 "treatments": [
    //                     "abc",
    //                     "def",
    //                     "this treatments will be returned only if post contains some, otherwise it will be empty list"
    //                 ],
    //                 "summary": "Hello A company is Saudi Arabia is using a spray to attempt to disinfect their workers. The workers walk through a tunnel."
    //             }
    //         ]
    //     },
    //     "search_type" : {
    //         "drug_search":
    //             [
    //                 {
    //                     "drug_name": "Absorica",
    //                     "drug_detail_page": "https://www.webmd.com/drugs/2/drug-162902/absorica-oral/details",
    //                     "drug_review_page": "https://www.webmd.com/drugs/drugreview-162902-Absorica-oral.aspx?drugid=162902&drugname=Absorica-oral&pageIndex=0&sortby=3&conditionFilter=-1"
    //                 },
    //                 {
    //                     "drug_name": "Accupril",
    //                     "drug_detail_page": "https://www.webmd.com/drugs/2/drug-3971/accupril-oral/details",
    //                     "drug_review_page": "https://www.webmd.com/drugs/drugreview-3971-Accupril-oral.aspx?drugid=3971&drugname=Accupril-oral&pageIndex=0&sortby=3&conditionFilter=-1",
    //                 }
    //             ]
    //     }
    // }

    // response json parsed from response
    const res = JSON.parse(response);

    if (res != '') {

        if(res.hasOwnProperty('related_symptoms') && res.related_symptoms.length > 0) {
            var newLabel = 'Related Symptoms: ';
            for (var i = 0; i < res.related_symptoms.length; i++) {
                if (i != 0) {
                    newLabel += ', ';
                }
                newLabel += res.related_symptoms[i];
            }
            $("#related_symptoms").html(newLabel);
            $("#related_symptoms").css('visibility', 'visible');
        }
        else {
            $("#related_symptoms").css('visibility', 'hidden');
        }


        if($('#search_selection').val() == 'disease_search') {
            $('div.response').css('display', 'inline-block');
            $('div.posts').css('display', 'inline-block');
            $('div.posts_title').css('display', 'inline-block');
            $('div.disease_title').css('display', 'inline-block');
            $('div.drug_title').css('display', 'none');
            $('div.drug').css('display', 'none');
            $('div.response').empty();
            $('div.posts').empty();
            $('div.drug').empty();

            if(res.results.hasOwnProperty('mayo_clinic')){
                    if (res.results.mayo_clinic.length > 0) {
                    var list = "";
                    for (var i = 0; i < 2; i++) {
                        var item = res.results.mayo_clinic[i];
                        list += "<ul>";
                        list += "<li class='title_li'>" + item.title + "</li>";
                        list += "<ul>";
                        list += "<li class = 'description'>" + item.summary.substring(0, 300) + "</li>";
                        list += "</ul>";
                        list += "</ul>";
                        list += "<hr class='hr_line'>";
                    }
                    setTimeout(function () {
                        $(".response").append(list);
                    });
                }
            }

            var list_posts = "";
            if(res.results.hasOwnProperty('web_md_mb')) {
                if (res.results.web_md_mb.length > 0) {
                    for (var i = 0; i < 2; i++) {
                        var item = res.results.web_md_mb[i];
                        list_posts += "<ul>";
                        list_posts += "<li class='title_li'>" + item.title + "</li>";
                        list_posts += "<ul>";
                        list_posts += "<li class='description'>" + item.summary.substring(0, 300) + " (<a href = '" + item.url + "' " + "target='_blank'" + "color ='blue'" + " >" + item.url + "</a>) </li>";
                        list_posts += "</ul>";
                        list_posts += "</ul>";
                        list_posts += "<hr class='hr_line'>"
                    }
                }
            }

            if(res.results.hasOwnProperty('patient_info')) {
                if (res.results.patient_info.length > 0) {
                    for (var i = 0; i < 2; i++) {
                        var item = res.results.patient_info[i];
                        list_posts += "<ul>";
                        list_posts += "<li class='title_li'>" + item.title + "</li>";
                        list_posts += "<ul>";
                        list_posts += "<li class='description'>" + item.summary.substring(0, 300) + " (<a href = '" + item.url + "' " + "target='_blank'" + "color ='blue'" + " >" + item.url + "</a>) </li>";
                        list_posts += "</ul>";
                        list_posts += "</ul>";
                        list_posts += "<hr class='hr_line'>"
                    }
                }
            }

            setTimeout(function () {
                $(".posts").append(list_posts);
            });
        }
        else
        if($('#search_selection').val() == 'drug_search') {
            $('div.response').css('display', 'none');
            $('div.posts').css('display', 'none');
            $('div.drug').css('display', 'inline-block');
            $('div.posts_title').css('display', 'none');
            $('div.disease_title').css('display', 'none');
            $('div.drug_title').css('display', 'inline-block');
            $('div.drug').empty();

            var drug_list = "";
            if(res.results.length != 0){
                for (var i = 0; i < 5; i++) {
                    var item = res.results[i];
                    drug_list += "<ul>";
                    drug_list += "<li class='title_li'>" + item.drug_name + "</li>";
                    drug_list += "<ul>";
                    // drug_list += "<li class='description'>" + " (<a href = '" + item.drug_review_page + "' " + "target='_blank'" + "color ='blue'" + " >" + item.drug_review_page + "</a>) </li>";
                    drug_list += "<li class='description'>" + " (<a href = '" + item.drug_review_page + "' " + "target='_blank'" + "color ='blue'" + " > Check user reviews" + "</a>) </li>";
                    drug_list += "</ul>";
                    drug_list += "</ul>";
                    drug_list += "<hr class='hr_line'>"
                }
                setTimeout(function () {
                    $(".drug").append(drug_list);
                });
            }
            else {
                $(".drug").append("<h4>No Matching Drugs Found.</h4>");
            }
        }
    } else {
        swal("Don't Worry! It doesn't seem like any disease as per the results!");
    }
}

function searchOptionChange() {
    const selectedValue = document.getElementById("search_selection").value;
    if (selectedValue === 'drug_search') {
        //reset the value to avoid unwanted search
        document.getElementById('search_box').value = '';
        document.getElementById("search_box").placeholder = "Type the disease name here...";
    }
    else {
        //reset the value to avoid unwanted search
        document.getElementById('search_box').value = '';
        document.getElementById("search_box").placeholder = "Type the symptoms here...";
    }
}