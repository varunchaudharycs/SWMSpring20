from pymetamap import MetaMap


class MetaMapWrapper(object):
    mm = MetaMap.get_instance('/Users/hemant/GithubWorkspace/MetaMap/public_mm/bin/metamap18')

    def __init__(self):
        pass

    def annotate(self, text):
        mm_request = [text]
        concepts, error = self.mm.extract_concepts(mm_request, [1, 2])
        extracted_data = {}
        symptoms = []
        diseases = []
        diagnostics = []
        for concept in concepts:
            # print(concept)
            if concept.semtypes == '[sosy]':
                # Sign or Symptom
                symptoms.append(concept.preferred_name)
            elif concept.semtypes == '[dsyn]':
                # Disease or Syndrome
                diseases.append(concept.preferred_name)
                pass
            elif concept.semtypes == '[diap]':
                # Diagnostic Procedure
                diagnostics.append(concept.preferred_name)

        if len(symptoms):
            extracted_data['symptoms'] = symptoms
        if len(diseases):
            extracted_data['diseases'] = diseases
        if len(diagnostics):
            extracted_data['diagnostics'] = diagnostics

        return extracted_data
