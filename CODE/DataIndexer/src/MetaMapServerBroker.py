from http.server import HTTPServer, BaseHTTPRequestHandler
from urllib import parse
from sys import argv
import json
from src.MetaMapWrapper import MetaMapWrapper


class Server(BaseHTTPRequestHandler):
    mmw = MetaMapWrapper()

    def _set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'application/json')
        self.end_headers()

    def do_HEAD(self):
        self._set_headers()

    def do_GET(self):
        print("Received a request..")
        self._set_headers()
        annotated_query = {}
        print(self.request)
        o = parse.urlparse(self.path)
        parsed_params = parse.parse_qs(o.query)
        search_query_str = parsed_params['query']
        extracted_data = self.mmw.annotate(search_query_str)
        if 'symptoms' in extracted_data:
            annotated_query['symptoms'] = extracted_data['symptoms']
        if 'diseases' in extracted_data:
            annotated_query['diseases'] = extracted_data['diseases']
        if 'diagnostics' in extracted_data:
            annotated_query['diagnostic_procedures'] = extracted_data['diagnostics']

        # TODO: fetch suggested symptoms using page rank and append as
        # extracted_data['symptoms_suggestion'] = list of symptoms
        encoded = json.dumps(extracted_data).encode()
        self.wfile.write(encoded)


def run(server_class=HTTPServer, handler_class=Server, port=8008):
    # server_address = ('', port)
    server_address = ('localhost', 8008)
    handler_class.protocol_version = "HTTP/1.0"
    httpd = server_class(server_address, handler_class)

    print('Starting httpd on port %d...' % port)
    httpd.serve_forever()


if __name__ == "__main__":
    if len(argv) == 2:
        run(port=int(argv[1]))
    else:
        run()