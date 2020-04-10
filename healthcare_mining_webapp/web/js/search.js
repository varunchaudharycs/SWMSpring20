function sendSearchRequest() {
    const searchQuery = $('#search_box').val();
    const selectedSearchOption = document.getElementById("search_selection").value;
    ajaxGetRequest(window.location.href + '/search?query=' + searchQuery + '&type=' + selectedSearchOption, handleSearchResponse)
}

function handleSearchResponse(response) {
    console.log(response)
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
