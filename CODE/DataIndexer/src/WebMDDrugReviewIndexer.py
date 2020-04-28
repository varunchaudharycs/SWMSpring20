import requests
import os
import json
import time

from src.MetaMapWrapper import MetaMapWrapper

CURRENT_DIR = os.path.dirname(__file__)

start_index = 5001
end_index = 10000


def index_json_file(file_path):
    mmw = MetaMapWrapper()
    with open(file_path) as json_file:
        data = json.load(json_file)
        failed_count = 0
        i = 0
        for review in data:
            # first index first 50000 reviews
            if i == end_index:
                break

            i += 1
            # skip already indexed data
            if i < start_index:
                continue

            # wait for 5 seconds before every request
            time.sleep(2)
            try:
                preprocessed_review = {}
                preprocessed_review['drug_name'] = review['drug_name']
                preprocessed_review['drug_detail_page'] = review['drug_detail_page']
                preprocessed_review['drug_review_page'] = review['drug_review_page']
                preprocessed_review['health_condition_name'] = review['health_condition_name']
                preprocessed_review['timestamp'] = review['timestamp']
                preprocessed_review['reviewer_name'] = review['reviewer_name']
                age_range = review['patient_age_range']
                if age_range == 'unknown':
                    preprocessed_review['patient_age_start'] = int(-1)
                    preprocessed_review['patient_age_end'] = int(-1)
                else:
                    patient_age_parts = review['patient_age_range'].split('-')
                    preprocessed_review['patient_age_start'] = int(patient_age_parts[0])
                    preprocessed_review['patient_age_end'] = int(patient_age_parts[1])

                preprocessed_review['patient_gender'] = review['patient_gender']
                preprocessed_review['treatment_duration'] = review['treatment_duration']
                preprocessed_review['reviewer_category'] = review['reviewer_category']
                review_comment = review['review_comment']
                preprocessed_review['review_comment'] = review_comment
                if len(review_comment) > 0:
                    extracted_data = mmw.annotate(review_comment)
                    if 'symptoms' in extracted_data:
                        preprocessed_review['symptoms'] = extracted_data['symptoms']
                    if 'diseases' in extracted_data:
                        preprocessed_review['diseases'] = extracted_data['diseases']
                    if 'diagnostics' in extracted_data:
                        preprocessed_review['diagnostic_procedures'] = extracted_data['diagnostics']

                preprocessed_review['num_of_people_found_useful'] = review['num_of_people_found_useful']
                preprocessed_review['effectiveness_rating'] = int(review['effectiveness_rating'])
                preprocessed_review['ease_of_use_rating'] = int(review['ease_of_use_rating'])
                preprocessed_review['satisfaction_rating'] = int(review['satisfaction_rating'])

                # send request to server
                r = requests.post('http://localhost:8080/healthcare_mining/index', params={"type": "drug_review"},
                                  json=preprocessed_review)
                if r.status_code == 500:
                    failed_count += 1

            except:
                print("Exception while indexing this review: " + str(review))
                failed_count += 1

    print("total number of failed requests: " + str(failed_count))

    # send final index commit request
    r = requests.post('http://localhost:8080/healthcare_mining/index', params={"type": "index_commit"},
                      json={"status": "ok"})
    print("Commit status: " + str(r.status_code))


if __name__ == "__main__":
    index_json_file(CURRENT_DIR + os.path.sep + "webmd_drugs_reviews.json")

# r = requests.post('http://localhost:8080/healthcare_mining/index', params={"type": "drug_review"}, json={
#       "author": "edithtrotman",
#       "post_time": "16 days ago",
#       "post_title": "Killerbees",
#       "post_content": "Several months ago I was attacked by bees, now if I get a mosquito bite or ant bite, any kind of insect bite I get a severe infection at the site. I’m concerned about what to expect from future bee sting.\nHave I developed an allergy to any type of bite. Is there a remedy.",
#       "like_count": "0",
#       "tags": []
#     })
# print(type(r))
# print(r.status_code)